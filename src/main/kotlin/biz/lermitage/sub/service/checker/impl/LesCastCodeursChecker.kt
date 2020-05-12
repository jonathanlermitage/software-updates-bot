package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

/**
 * Les Cast Codeurs podcast checker.
 */
@Service
class LesCastCodeursChecker : Checker {

    override fun check(): SoftwareUpdate {
        val body = Jsoup.connect("https://lescastcodeurs.com")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        val version = body.getElementsByClass("blog-post-title")[0].text()

        return SoftwareUpdate(
            "Les Cast Codeurs (French podcast)",
            "https://lescastcodeurs.com",
            version)
    }
}
