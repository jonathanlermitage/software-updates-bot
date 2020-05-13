package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import org.jsoup.Jsoup

/**
 * PostgreSQL checker.
 * @param majorVersion "major" part of version
 */
abstract class PostgresChecker(private val majorVersion: String) : Checker {

    override fun check(): SoftwareUpdate {
        val body = Jsoup.connect("https://www.postgresql.org/ftp/source/")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        val version = body.getElementsByAttributeValueStarting("href", "v${majorVersion}.")[0].attr("href")
            .replace("v", "").replace("/", "")

        return SoftwareUpdate(
            "PostgreSQL $majorVersion",
            "https://www.postgresql.org/download/",
            version)
    }
}
