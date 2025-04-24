package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.model.jetbrains.marketplace.PluginsApiResponse
import biz.lermitage.sub.service.report.Reporter
import biz.lermitage.sub.service.scrapper.Scrapper
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class JetBrainsMarketplacePluginsAllReporterImpl(private val conf: LocalAppConf) : Reporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var scrapper: Scrapper

    override fun generate(updates: List<SoftwareUpdate>): Boolean {
        generateReport()
        return true
    }

    private fun generateReport() {
        val reportFile = File(conf.reportFile.jbMarketplacePluginsLatestAll)
        reportFile.parentFile.mkdirs()
        reportFile.delete()
        write(reportFile)
    }

    private fun write(reportFile: File) {
        val json = scrapper.fetchText(
            "https://plugins.jetbrains.com/api/searchPlugins?excludeTags=internal" +
                "&excludeTags=theme&max=100&offset=0&orderBy=publish%20date" +
                "&products=androidstudio&products=appcode&products=aqua&products=clion" +
                "&products=dataspell&products=dbe&products=fleet&products=go" +
                "&products=idea&products=idea_ce&products=mps&products=phpstorm" +
                "&products=pycharm&products=rider&products=ruby&products=rust" +
                "&products=webstorm&products=writerside"
        )
        val apiResponse = Gson().fromJson(json, PluginsApiResponse::class.java)
        val plugins = apiResponse.plugins

        logger.info("Found ${plugins.size} plugins")
        plugins.forEach { plugin ->
            run {
                logger.info("- $plugin")
            }
        }

        //FileUtils.writeLines(reportFile, Globals.reportCharset().name(), lines)
        logger.info("saved UC report file to: ${reportFile.absolutePath}")
    }
}
