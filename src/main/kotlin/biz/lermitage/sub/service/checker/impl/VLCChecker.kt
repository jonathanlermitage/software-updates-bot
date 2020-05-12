package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

/**
 * VLC checker.
 */
@Service
class VLCChecker : Checker {

    override fun check(): SoftwareUpdate {
        val body = Jsoup.connect("https://www.videolan.org/vlc/")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        var version = body.getElementsByAttributeValue("id", "downloadDetails")[0].text()
        if (version.contains("•"))
            version = version.substring(0, version.indexOf("•")).replace("Version", "").trim()

        return SoftwareUpdate(
            "VLC",
            "https://www.videolan.org/vlc/",
            version)
    }
}
