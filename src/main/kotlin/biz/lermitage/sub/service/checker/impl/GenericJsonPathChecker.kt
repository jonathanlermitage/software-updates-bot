package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import com.jayway.jsonpath.JsonPath
import org.jsoup.Jsoup

abstract class GenericJsonPathChecker(
    private val url: String,
    private val jsonpath: String,
    private val name: String,
    private val website: String) : Checker {

    override fun check(): SoftwareUpdate {
        val json = Jsoup.connect(url)
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .get().text()
        val version = JsonPath.read<String>(json, jsonpath)

        return SoftwareUpdate(
            name,
            website,
            version)
    }
}
