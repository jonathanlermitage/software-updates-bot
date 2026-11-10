package biz.lermitage.sub.service.dreport.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.service.dreport.DReporter
import com.rometools.rome.feed.synd.SyndContentImpl
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndEntryImpl
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.feed.synd.SyndFeedImpl
import com.rometools.rome.io.SyndFeedOutput
import org.apache.commons.io.FileUtils
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.w3c.dom.Element
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileWriter
import java.net.URI
import java.net.URLDecoder
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Base64
import java.util.Date
import java.util.Locale
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Scraps the software published for the Tuxedo InfinityBook Pro Gen10 AMD
 * on the mytuxedo.de Nextcloud share, then generates an RSS feed, a Markdown
 * report (ordered by date) and a status file.
 */
@Service
class TuxedoInfinityBookProGen10AmdReporterImpl(private val conf: LocalAppConf) : DReporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    data class TuxedoSoftware(val name: String, val fileName: String, val publishDate: LocalDate)

    override fun generate() {
        try {
            val software = fetchSoftware()
            writeRss(software)
            writeMarkdown(software)
            writeStatus("OK")
        } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
            writeStatus("FAILED\n\n<pre>${ExceptionUtils.getStackTrace(e)}</pre>")
            throw e
        }
    }

    private fun fetchSoftware(): List<TuxedoSoftware> {
        val auth = Base64.getEncoder().encodeToString("$SHARE_TOKEN:".toByteArray(StandardCharsets.UTF_8))
        val request = HttpRequest.newBuilder(URI.create(WEBDAV_URL))
            .method("PROPFIND", HttpRequest.BodyPublishers.noBody())
            .header("Authorization", "Basic $auth")
            .header("Depth", "1")
            .header("User-Agent", Globals.SCRAPPER_USER_AGENT)
            .timeout(Duration.ofSeconds(30))
            .build()
        logger.info("[*  ] fetching $WEBDAV_URL")
        val response = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(30))
            .build()
            .send(request, HttpResponse.BodyHandlers.ofByteArray())
        if (response.statusCode() !in 200..299) {
            throw IllegalStateException("Unexpected HTTP status ${response.statusCode()} from $WEBDAV_URL")
        }
        val softwares = parseWebDav(response.body())
        if (softwares.isEmpty()) {
            throw IllegalStateException("No software found at $WEBDAV_URL")
        }
        return softwares.sortedWith(compareByDescending<TuxedoSoftware> { it.publishDate }.thenBy { it.name })
    }

    internal fun parseWebDav(xml: ByteArray): List<TuxedoSoftware> {
        val factory = DocumentBuilderFactory.newInstance()
        factory.isNamespaceAware = true
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
        val doc = factory.newDocumentBuilder().parse(ByteArrayInputStream(xml))
        val responses = doc.getElementsByTagNameNS(DAV_NS, "response")
        val res = ArrayList<TuxedoSoftware>()
        for (i in 0 until responses.length) {
            val r = responses.item(i) as Element
            val href = r.getElementsByTagNameNS(DAV_NS, "href").item(0)?.textContent ?: continue
            val modified = r.getElementsByTagNameNS(DAV_NS, "getlastmodified").item(0)?.textContent ?: continue
            val fileName =
                URLDecoder.decode(href.trimEnd('/').substringAfterLast('/').replace("+", "%2B"), StandardCharsets.UTF_8)
            if (!fileName.lowercase().endsWith(".zip")) {
                continue // ignore folders and text files
            }
            res.add(TuxedoSoftware(fileName.dropLast(".zip".length), fileName, parseDate(modified)))
        }
        return res
    }

    /** Parse an English or German date, and return it as a [LocalDate]. */
    internal fun parseDate(raw: String): LocalDate {
        val text = raw.trim()
        for (locale in listOf(Locale.ENGLISH, Locale.GERMAN)) {
            try {
                return ZonedDateTime.parse(text, DateTimeFormatter.RFC_1123_DATE_TIME.withLocale(locale))
                    .withZoneSameInstant(ZoneOffset.UTC).toLocalDate()
            } catch (_: DateTimeParseException) {
                // try next locale
            }
        }
        val translated = translateGermanToEnglish(text)
        return ZonedDateTime.parse(translated, DateTimeFormatter.RFC_1123_DATE_TIME)
            .withZoneSameInstant(ZoneOffset.UTC).toLocalDate()
    }

    private fun translateGermanToEnglish(text: String): String {
        var res = text
        GERMAN_TO_ENGLISH.forEach { (de, en) -> res = res.replace(Regex("\\b$de\\b", RegexOption.IGNORE_CASE), en) }
        return res
    }

    private fun downloadUrl(software: TuxedoSoftware): String =
        "$SHARE_URL/download?path=" + URLEncoder.encode("/$SHARE_DIR", StandardCharsets.UTF_8) +
            "&files=" + URLEncoder.encode(software.fileName, StandardCharsets.UTF_8)

    private fun writeRss(software: List<TuxedoSoftware>) {
        val file = File(conf.reportFile.tuxedoInfinityBookProGen10AmdRss)
        file.parentFile.mkdirs()
        file.delete()

        val feed: SyndFeed = SyndFeedImpl()
        feed.feedType = "rss_2.0"
        feed.title = "Tuxedo InfinityBook Pro Gen10 AMD"
        feed.description = "Software published for the Tuxedo InfinityBook Pro Gen10 AMD"
        feed.link = "https://github.com/jonathanlermitage/software-updates-bot/raw/master/${file.path}"
        feed.author = "Jonathan Lermitage"
        feed.encoding = Globals.reportCharset().name()
        feed.entries = software.map { s ->
            val entry: SyndEntry = SyndEntryImpl()
            val date = Date.from(s.publishDate.atStartOfDay().toInstant(ZoneOffset.UTC))
            entry.title = "${s.name} - ${formatDate(s.publishDate)}"
            entry.link = downloadUrl(s)
            entry.uri = "${s.fileName}#${formatDate(s.publishDate)}"
            entry.publishedDate = date
            val description = SyndContentImpl()
            description.type = "text/plain"
            description.value = "${s.fileName}\npublished: ${formatDate(s.publishDate)}"
            entry.description = description
            entry
        }
        FileWriter(file, Globals.reportCharset()).use { SyndFeedOutput().output(feed, it) }
        logger.info("saved report file to: ${file.absolutePath}")
    }

    private fun writeMarkdown(softwares: List<TuxedoSoftware>) {
        val file = File(conf.reportFile.tuxedoInfinityBookProGen10AmdMdByDate)
        file.parentFile.mkdirs()
        file.delete()
        val lines = ArrayList<String>()
        lines.add("|Software|Publish date|Download|")
        lines.add("|---|---|---|")
        softwares.forEach { s ->
            lines.add("|${s.name}|${formatDate(s.publishDate)}|[${s.fileName}](${downloadUrl(s)})|")
        }
        FileUtils.writeLines(file, Globals.reportCharset().name(), lines)
        logger.info("saved Markdown report file to: ${file.absolutePath}")
    }

    private fun writeStatus(msg: String) {
        val file = File(conf.statusFile.tuxedoInfinityBookProGen10AmdMd)
        file.parentFile.mkdirs()
        if (file.exists() && FileUtils.readFileToString(file, Globals.reportCharset().name()) == msg) {
            return
        }
        FileUtils.write(file, msg, Globals.reportCharset().name(), false)
    }

    private fun formatDate(date: LocalDate): String = date.format(OUTPUT_DATE_FORMAT)

    companion object {
        private const val DAV_NS = "DAV:"
        private const val SHARE_TOKEN = "ZeB8FTf8CrpEtJr"
        private const val SHARE_DIR = "InfinityBook_Pro_Series/InfinityBook_Pro_Gen10_AMD"
        private const val SHARE_URL = "https://mytuxedo.de/index.php/s/$SHARE_TOKEN"
        private const val WEBDAV_URL = "https://mytuxedo.de/public.php/webdav/$SHARE_DIR/"
        private val OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        private val GERMAN_TO_ENGLISH = listOf(
            "Mo" to "Mon", "Di" to "Tue", "Mi" to "Wed", "Do" to "Thu", "Fr" to "Fri", "Sa" to "Sat", "So" to "Sun",
            "Jan" to "Jan", "Feb" to "Feb", "Mär" to "Mar", "Mrz" to "Mar", "Apr" to "Apr", "Mai" to "May",
            "Jun" to "Jun", "Jul" to "Jul", "Aug" to "Aug", "Sep" to "Sep", "Okt" to "Oct", "Nov" to "Nov",
            "Dez" to "Dec"
        )
    }
}
