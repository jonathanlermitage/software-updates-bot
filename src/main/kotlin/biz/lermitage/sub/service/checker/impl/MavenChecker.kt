package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

/**
 * Maven checker.
 */
@Service
class MavenChecker : Checker {

    override fun check(): SoftwareUpdate {
        val body = Jsoup.connect("https://maven.apache.org/docs/history.html")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        val version = body.getElementsByTag("table")[0].getElementsByTag("tr")[1].getElementsByTag("td")[1].text()

        return SoftwareUpdate(
            "Maven",
            "https://maven.apache.org/download.cgi",
            version)
    }
}
