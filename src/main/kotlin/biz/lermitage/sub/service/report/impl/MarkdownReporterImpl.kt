package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.report.Reporter
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.util.stream.Collectors

@Service
class MarkdownReporterImpl(private val conf: LocalAppConf) : Reporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun generate(updates: List<SoftwareUpdate>): Boolean {
        generateOrderedDefault(updates)
        generateOrderedByDateDesc(updates)
        return true
    }

    private fun generateOrderedDefault(updates: List<SoftwareUpdate>) {
        val reportFile = File(conf.reportFile.md)
        reportFile.parentFile.mkdirs()
        reportFile.delete()
        write(reportFile, updates.stream().sorted().collect(Collectors.toList()))
    }

    private fun generateOrderedByDateDesc(updates: List<SoftwareUpdate>) {
        val reportFile = File(conf.reportFile.mdByDate)
        reportFile.parentFile.mkdirs()
        reportFile.delete()
        write(reportFile, updates.stream().sorted(SoftwareUpdate.comparatorByDateDescThenByName).collect(Collectors.toList()))
    }

    private fun write(reportFile: File, updates: List<SoftwareUpdate>) {
        val lines = ArrayList<String>()
        lines.add("|Software|Version|Website|Check date|")
        lines.add("|---|---|---|---|")
        updates.stream().forEach { su: SoftwareUpdate ->
            var shortWebsite = su.website.replace("https://", "").replace("http://", "")
            if (shortWebsite.contains("/")) {
                shortWebsite = shortWebsite.subSequence(0, shortWebsite.indexOf("/")).toString()
            }
            val prefix = if (su.logo == Logo.NONE || su.logo == null) {
                ""
            } else {
                "![logo](../media/logo/${su.logo.img}) "
            }
            lines.add("|$prefix${su.name}|${su.version}|[$shortWebsite](${su.website})|${su.checkDate}|")
        }

        FileUtils.writeLines(reportFile, Globals.reportCharset().name(), lines)
        logger.info("saved Markdown report file to: ${reportFile.absolutePath}")
    }
}
