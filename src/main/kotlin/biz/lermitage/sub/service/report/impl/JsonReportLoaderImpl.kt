package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.Report
import biz.lermitage.sub.service.report.JsonReportLoader
import org.apache.commons.io.FileUtils
import org.springframework.stereotype.Service
import java.io.File

@Service
class JsonReportLoaderImpl(private val conf: LocalAppConf) : JsonReportLoader {

    override fun load(): Report {
        val reportFile = File(conf.reportFile.json)
        if (!reportFile.exists()) {
            return Report(emptyList())
        }
        val reportFileContent = FileUtils.readFileToString(reportFile, Globals.reportCharset())
        return Globals.gsonForReport()
            .fromJson(reportFileContent, Report::class.java)
    }
}
