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
 * Nilesoft Shell checker.
 */
@Service
class NilesoftShellChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://nilesoft.org/download/changes")
        val versions = body.getElementsByTag("strong")

        val versionTitleFound = versions.toList().find { element: Element -> element.text().lowercase().contains("version ") }
        val version = versionTitleFound!!.text().lowercase().replace("version ", "").trim()

        return SoftwareUpdate(
            listOf(Category.MS_WINDOWS.label),
            "Nilesoft Shell",
            "https://nilesoft.org/",
            version,
            logo = Logo.NILESOFTSHELL
        )
    }
}
