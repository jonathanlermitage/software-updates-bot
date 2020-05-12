package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.Report
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.report.JsonReportLoader
import biz.lermitage.sub.service.report.Reporter
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import kotlin.streams.toList

@Service
class JsonReporterImpl(private val conf: LocalAppConf,
                       private val jsonReportLoader: JsonReportLoader) : Reporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun generate(updates: List<SoftwareUpdate>): Boolean {

        val reportFile = File(conf.reportFile.json)
        reportFile.parentFile.mkdirs()

        val previousUpdates = jsonReportLoader.load().updates

        return if (updates != previousUpdates) {
            val mergedReportJson = Globals.gsonForReport()
                .toJson(Report(updates.stream().sorted().toList()), Report::class.java)

            reportFile.delete()
            FileUtils.write(reportFile, mergedReportJson, Globals.reportCharset())
            logger.info("saved JSON report file to: ${reportFile.absolutePath}")
            true
        } else {
            false
        }
    }
}
