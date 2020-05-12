package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.stereotype.Service

/**
 * Inkscape checker.
 */
@Service
class InkscapeChecker : Checker {

    override fun check(): SoftwareUpdate {
        val body = Jsoup.connect("https://inkscape.org/release/")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        val titles = body.getElementsByTag("h2")
        val versionTitleFound = titles.toList().find { element: Element -> element.text().toLowerCase().contains("inkscape ") }
        val version = versionTitleFound!!.text().toLowerCase().replace("inkscape", "").trim()

        return SoftwareUpdate(
            "Inkscape",
            "https://inkscape.org",
            version)
    }
}
