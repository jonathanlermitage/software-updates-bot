package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import org.jsoup.Jsoup

/**
 * MariaDB checker.
 * @param versionLeftPart "major.minor" part of version, without the ".patch" part
 */
abstract class MariaDBChecker(private val versionLeftPart: String) : Checker {

    override fun check(): SoftwareUpdate {
        val body = Jsoup.connect("https://downloads.mariadb.org/mariadb/+releases/")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .execute().parse().body()
        val version = body.getElementsByAttributeValueStarting("href", "/mariadb/${versionLeftPart}.")[0].text()

        return SoftwareUpdate(
            "MariaDB $versionLeftPart",
            "https://downloads.mariadb.org",
            version)
    }
}
