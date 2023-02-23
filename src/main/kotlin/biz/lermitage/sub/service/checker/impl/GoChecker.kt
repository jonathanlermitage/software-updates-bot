package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * GoLang checker.
 */
@Service
class GoChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://golang.org/dl/")
        val titles = body.getElementsByTag("h3")
        val versionTitleFound = titles.toList().find { element: Element -> element.className().contains("toggleButton") }
        var version = versionTitleFound!!.text().trim()

        return SoftwareUpdate(
            listOf(Category.GO.label),
            "Go",
            "https://golang.org/dl/",
            version,
            logo = Logo.GO
        )
    }
}
