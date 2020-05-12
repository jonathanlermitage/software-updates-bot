package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import org.jsoup.Jsoup

/**
 * NodeJS update checker.
 * @param type "LTS", "Current". For information only
 * @param downloadButtonIdx position of download button on NodeJS website. The first is for NodeJS LTS, the second for latest NodeJS
 */
abstract class NodeJSChecker(
    private val type: String,
    private val downloadButtonIdx: Int) : Checker {

    override fun check(): SoftwareUpdate {
        val body = Jsoup.connect("https://nodejs.org/en/")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        val version = body.getElementsByClass("home-downloadbutton")[downloadButtonIdx].attr("data-version")

        return SoftwareUpdate(
            "NodeJS $type",
            "https://nodejs.org",
            version)
    }
}
