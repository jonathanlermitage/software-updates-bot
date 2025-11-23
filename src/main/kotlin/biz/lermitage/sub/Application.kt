package biz.lermitage.sub

import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.DetailedException
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.dreport.DReporter
import biz.lermitage.sub.service.report.JsonReportLoader
import biz.lermitage.sub.service.report.Reporter
import biz.lermitage.sub.service.report.StatusReporter
import biz.lermitage.sub.service.report.UpdatesMerger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.retry.annotation.EnableRetry
import java.lang.management.ManagementFactory
import java.util.TimeZone

@SpringBootApplication
@EnableConfigurationProperties(LocalAppConf::class)
@EnableRetry
class Application : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var checkers: List<Checker>

    @Autowired
    lateinit var reporters: List<Reporter>

    @Autowired
    lateinit var dReporters: List<DReporter>

    @Autowired
    lateinit var jsonReportLoader: JsonReportLoader

    @Autowired
    lateinit var updatesMerger: UpdatesMerger

    @Autowired
    lateinit var statusReporter: StatusReporter

    @Suppress("TooGenericExceptionCaught") // we want to catch all exceptions to skip bad reports
    override fun run(vararg args: String) {
        logGCStats()
        logMemoryStats()

        val latestUpdates = ArrayList<SoftwareUpdate>()
        val errors = ArrayList<DetailedException>()
        val versionPattern = Regex(".*\\d+.*")

        checkers.forEach { checker: Checker ->
            try {
                val check = checker.check()
                logger.info("[  *] fetched $check")
                val version = check.version
                if (version.isEmpty()
                    || version.lowercase() == "archived"
                    || version.lowercase() == "archive"
                    || !version.matches(versionPattern)
                ) {
                    throw NumberFormatException("Bad version number: $version")
                }
                latestUpdates.add(check)
            } catch (e: Exception) {
                logger.warn("Checker ${checker::class.java} failed, ignoring", e)
                errors.add(DetailedException("Checker ${checker::class.java} failed, ignoring", e))
            }
        }

        val previousReport = jsonReportLoader.load()
        val mergedUpdates = updatesMerger.merge(previousReport.updates, latestUpdates)

        reporters.forEach { reporter: Reporter ->
            try {
                reporter.generate(mergedUpdates)
            } catch (e: Exception) {
                logger.warn("Reporter ${reporter::class.java} failed, ignoring", e)
                errors.add(DetailedException("Reporter ${reporter::class.java} failed, ignoring", e))
            }
        }

        dReporters.forEach { dReporter ->
            try {
                dReporter.generate()
            } catch (e: Exception) {
                logger.warn("DReporter ${dReporter::class.java} failed, ignoring", e)
                errors.add(DetailedException("Reporter ${dReporter::class.java} failed, ignoring", e))
            }
        }

        if (errors.isEmpty()) {
            statusReporter.generateSuccess()
        } else {
            statusReporter.generateError(errors)
        }

        logGCStats()
        logMemoryStats()
    }

    private fun logGCStats() {
        var totalGarbageCollections: Long = 0
        var garbageCollectionTime: Long = 0
        for (gc in ManagementFactory.getGarbageCollectorMXBeans()) {
            totalGarbageCollections += gc.collectionCount
            garbageCollectionTime += gc.collectionTime
        }
        val uptime = ManagementFactory.getRuntimeMXBean().uptime
        val gcActivityRatio = garbageCollectionTime * 100 / uptime
        logger.info(
            "GC stats:\n - total GC collections: {}\n - total GC collection time: {}ms (activity ratio: {}%)\n - JVM uptime: {}ms",
            totalGarbageCollections, garbageCollectionTime, gcActivityRatio, uptime
        )
    }

    private fun logMemoryStats() {
        val runtime = Runtime.getRuntime()
        val maxMemory = runtime.maxMemory()
        val allocatedMemory = runtime.totalMemory()
        val freeMemory = runtime.freeMemory()
        val mb = 1024 * 1024
        logger.info(
            "Memory stats:\n - free memory: {}MB,\n - allocated memory: {}MB\n - max memory: {}MB\n - total free memory: {}MB",
            freeMemory / mb,
            allocatedMemory / mb,
            maxMemory / mb,
            (freeMemory + (maxMemory - allocatedMemory)) / mb
        )
    }
}

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<Application>(*args)
}
