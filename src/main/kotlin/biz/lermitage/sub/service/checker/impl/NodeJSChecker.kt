package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired

/**
 * NodeJS update checker.
 * @param type "LTS", "Current". For information only
 * @param downloadButtonIdx position of download button on NodeJS website. The first is for NodeJS LTS, the second for latest NodeJS
 */
abstract class NodeJSChecker(
    private val type: String,
    private val downloadButtonIdx: Int
) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://nodejs.org/en/")
        val version = body.getElementsByClass("home-downloadbutton")[downloadButtonIdx].attr("data-version")

        return SoftwareUpdate(
            listOf(Category.NODEJS.label),
            "NodeJS $type",
            "https://nodejs.org",
            version,
            logo = Logo.NODEJS
        )
    }
}
