package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.report.Reporter
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.util.stream.Collectors

@Service
class PropertiesReporterImpl(private val conf: LocalAppConf) : Reporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun generate(updates: List<SoftwareUpdate>): Boolean {
        generateOrderedDefault(updates)
        return true
    }

    private fun generateOrderedDefault(updates: List<SoftwareUpdate>) {
        val reportFile = File(conf.reportFile.props)
        reportFile.parentFile.mkdirs()
        reportFile.delete()
        write(reportFile, updates.stream().sorted().collect(Collectors.toList()))
    }

    private fun write(reportFile: File, updates: List<SoftwareUpdate>) {
        val lines = ArrayList<String>()
        updates.stream().forEach { su: SoftwareUpdate ->
            val k = su.name.lowercase()
                .replace(Regex("[^a-zA-Z\\d]"), "")
            val v = su.version
            lines.add("$k=$v")
        }

        FileUtils.writeLines(reportFile, Globals.reportCharset().name(), lines)
        logger.info("saved Properties report file to: ${reportFile.absolutePath}")
    }
}
