package biz.lermitage.sub.service.dreport.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.jetbrains.marketplace.PluginsApiResponse
import biz.lermitage.sub.service.dreport.DReporter
import biz.lermitage.sub.service.scrapper.Scrapper
import com.google.gson.Gson
import com.rometools.rome.feed.synd.SyndContentImpl
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.io.SyndFeedOutput
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.io.File
import java.io.FileWriter
import java.util.Date

open abstract class AbstractJetBrainsPluginsReporter(private val vendor: String? = null) : DReporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var scrapper: Scrapper

    abstract fun getReportFilePath(): String

    override fun generate() {
        val reportFile = File(getReportFilePath())
        reportFile.parentFile.mkdirs()
        reportFile.delete()
        write(reportFile)
    }

    private fun write(reportFile: File) {
        val json = scrapper.fetchText(
            "https://plugins.jetbrains.com/api/searchPlugins?excludeTags=internal" +
                "&excludeTags=theme&max=200&offset=0&orderBy=publish%20date" +
                "&products=androidstudio&products=appcode&products=aqua&products=clion" +
                "&products=dataspell&products=dbe&products=fleet&products=go" +
                "&products=idea&products=idea_ce&products=mps&products=phpstorm" +
                "&products=pycharm&products=rider&products=ruby&products=rust" +
                "&products=webstorm&products=writerside"
        )
        val apiResponse = Gson().fromJson(json, PluginsApiResponse::class.java)
        val plugins = apiResponse.plugins

        val feed: SyndFeed = SyndFeedImpl()
        feed.feedType = "rss_2.0"
        feed.title = "jb-marketplace-plugins-jetbrains"
        feed.description = "The latest JetBrains plugins on the JetBrains marketplace"
        feed.link = "https://github.com/jonathanlermitage/software-updates-bot/raw/master/${getReportFilePath()}"
        feed.author = "Jonathan Lermitage"
        feed.encoding = Globals.reportCharset().name()
        val entries = ArrayList<SyndEntry>()

        plugins.forEach { p ->
            run {
                if (vendor == null || p.vendor.name == vendor) {
                    val entry = SyndEntryImpl()

                    entry.title = "${p.name} - ${Date(p.cdate)}"
                    entry.link = "https://plugins.jetbrains.com/${p.link}"
                    entry.publishedDate = Date(p.cdate)

                    val description = SyndContentImpl()
                    description.type = "text/plain"
                    description.value = "${p.preview}\n\n" +
                        "vendor: ${p.vendor.name}\n" +
                        "downloads: ${p.downloads}\n" +
                        "pricingModel: ${p.pricingModel}\n" +
                        "xmlId: ${p.xmlId}"
                    entry.description = description

                    entries.add(entry)
                }
            }
        }
        feed.entries = entries
        val output = SyndFeedOutput()
        val fileWriter = FileWriter(reportFile)
        fileWriter.use { output.output(feed, fileWriter) }

        logger.info("saved UC report file to: ${reportFile.absolutePath}")
    }
}
