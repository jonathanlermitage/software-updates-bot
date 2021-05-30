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
 * Inkscape checker.
 */
@Service
class InkscapeChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://inkscape.org/release/")
        val titles = body.getElementsByTag("h2")
        titles.addAll(body.getElementsByTag("h1"))
        val versionTitleFound = titles.toList().find { element: Element -> element.text().lowercase().contains("inkscape ") }
        val version = versionTitleFound!!.text().lowercase().replace("inkscape", "").trim()

        return SoftwareUpdate(
            listOf(Category.SVG_EDITOR.label, Category.INKSCAPE.label),
            "Inkscape",
            "https://inkscape.org",
            version,
            logo = Logo.INKSCAPE)
    }
}
