package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.DetailedException
import biz.lermitage.sub.service.report.StatusReporter
import com.rometools.rome.feed.synd.SyndContentImpl
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.io.SyndFeedOutput
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter
import java.util.*
import java.util.function.Consumer

@Service
class StatusReporterImpl(private val conf: LocalAppConf) : StatusReporter {

    override fun generateError(errors: List<DetailedException>) {
        writeStatusReports("FAILED", exceptionsToTxt(errors))
    }

    override fun generateSuccess() {
        writeStatusReports("OK", "OK")
    }

    private fun exceptionsToTxt(errors: List<DetailedException>): String {
        val txt = StringBuilder()
        txt.append("FAILED\n\n<pre>")
        errors.forEach(Consumer { txt.append(it.msg).append("\n").append(getCleanStacktrace(it)).append("\n") })
        txt.append("</pre>")
        return txt.toString()
    }

    private fun writeStatusReports(title: String, msg: String) {
        val mdStatusFile = File(conf.statusFile.md)

        // -------------- MD

        val prevStatus = if (mdStatusFile.exists()) {
            FileUtils.readFileToString(mdStatusFile, Globals.reportCharset().name())
        } else {
            ""
        }
        if (prevStatus == msg) {
            return
        }
        mdStatusFile.parentFile.mkdirs()
        mdStatusFile.delete()
        FileUtils.write(mdStatusFile, msg, Globals.reportCharset().name())

        // -------------- Atom

        val atomStatusFile = File(conf.statusFile.atom)
        atomStatusFile.parentFile.mkdirs()
        atomStatusFile.delete()

        val feed: SyndFeed = SyndFeedImpl()
        feed.feedType = "atom_0.3"
        feed.title = "software-updates-bot status"
        feed.description = "software-updates-bot status - https://github.com/jonathanlermitage/software-updates-bot"
        feed.link = "https://github.com/jonathanlermitage/software-updates-bot/raw/master/${conf.statusFile.atom}"
        feed.author = "Jonathan Lermitage"
        feed.encoding = Globals.reportCharset().name()
        feed.publishedDate = Date()
        val entries = ArrayList<SyndEntry>()
        val entryDescription = SyndContentImpl()
        entryDescription.type = "text/html"
        entryDescription.value = msg
        val entry = SyndEntryImpl()
        entry.title = title + " -- " + Date()
        entry.publishedDate = Date()
        entry.description = entryDescription
        entry.uri = ""
        entries.add(entry)
        feed.entries = entries
        val atomOutput = SyndFeedOutput()
        val atomFileWriter = FileWriter(atomStatusFile)
        atomFileWriter.use { atomOutput.output(feed, atomFileWriter) }

        // -------------- RSS

        val rssStatusFile = File(conf.statusFile.rss)
        rssStatusFile.parentFile.mkdirs()
        rssStatusFile.delete()
        val rssOutput = SyndFeedOutput()
        feed.feedType = "rss_2.0"
        feed.link = "https://github.com/jonathanlermitage/software-updates-bot/raw/master/${conf.statusFile.rss}"
        val rssFileWriter = FileWriter(rssStatusFile)
        rssFileWriter.use { rssOutput.output(feed, rssFileWriter) }
    }

    private fun getCleanStacktrace(ex: DetailedException): String {
        val stacktrace: String = ExceptionUtils.getStackTrace(ex.error)
        return stacktrace.replace(Regex("CGLIB\\$\\$[a-zA_Z0-9]+"), "CGLIB")
    }
}
