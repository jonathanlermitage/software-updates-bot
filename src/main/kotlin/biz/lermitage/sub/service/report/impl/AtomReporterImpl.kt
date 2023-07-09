package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.report.Reporter
import com.rometools.rome.feed.synd.SyndCategoryImpl
import com.rometools.rome.feed.synd.SyndContentImpl
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.io.SyndFeedOutput
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter

@Suppress("DuplicatedCode")
@Service
class AtomReporterImpl(private val conf: LocalAppConf) : Reporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun generate(updates: List<SoftwareUpdate>): Boolean {
        val reportFile = File(conf.reportFile.atom)
        reportFile.parentFile.mkdirs()
        reportFile.delete()

        val feed: SyndFeed = SyndFeedImpl()
        feed.feedType = "atom_0.3"
        feed.title = "software-updates-bot"
        feed.description = "Popular stuff updates - https://github.com/jonathanlermitage/software-updates-bot"
        feed.link = "https://github.com/jonathanlermitage/software-updates-bot/raw/master/${conf.reportFile.atom}"
        feed.author = "Jonathan Lermitage"
        feed.encoding = Globals.reportCharset().name()
        feed.categories = Category.values().map { category: Category ->
            val catObj = SyndCategoryImpl()
            catObj.name = category.label
            return@map catObj
        }.toList()

        val entries = ArrayList<SyndEntry>()
        updates.stream().sorted(SoftwareUpdate.comparatorByDateDescThenByName).forEach { su: SoftwareUpdate ->
            val description = SyndContentImpl()
            description.type = "text/plain"
            description.value = "${su.name} -- ${su.version} -- ${su.checkDate}"

            val entry = SyndEntryImpl()
            entry.title = "${su.name} -- ${su.version}"
            entry.link = su.website
            entry.publishedDate = su.checkDate
            entry.description = description
            entry.uri = ""
            entry.categories = su.categories.map { cat: String ->
                val catObj = SyndCategoryImpl()
                catObj.name = cat
                return@map catObj
            }.toList()

            entries.add(entry)
        }
        feed.entries = entries

        val output = SyndFeedOutput()
        val fileWriter = FileWriter(reportFile)
        fileWriter.use { output.output(feed, fileWriter) }

        logger.info("saved RSS feed report file to: ${reportFile.absolutePath}")
        return true
    }
}
