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
 * Visual C++ Redistributable Runtimes All-in-One checker.
 */
@Service
class VCRedistChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://www.techpowerup.com/download/visual-c-redistributable-runtime-package-all-in-one/")
        val titles = body.getElementsByTag("h3")
        val versionTitleFound = titles.toList()
            .find { element: Element ->
                element.className().contains("title")
                    && element.text().contains("Visual C++ Redistributable Runtimes All-in-One", ignoreCase = true)
            }
        val version = versionTitleFound!!.text()
            .replace("Visual C++ Redistributable Runtimes All-in-One", "", ignoreCase = true)
            .trim()

        return SoftwareUpdate(
            listOf(Category.REDIST.label, Category.MS_WINDOWS.label),
            "Visual C++ Redistributable Runtimes All-in-One",
            "https://www.techpowerup.com/download/visual-c-redistributable-runtime-package-all-in-one/",
            version,
            logo = Logo.VC_REDIST
        )
    }
}
