package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

// disabled for now

/**
 * JetBrains JBR checker.
 */
//@Service
class JBRChecker : Checker {

    //@Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://github.com/JetBrains/JetBrainsRuntime/releases/latest", executeJS = true)
        val version = body.getElementsByTag("h1")[3].text().replace("VeraCrypt version", "", ignoreCase = true).trim()

        return SoftwareUpdate(
            listOf(Category.JETBRAINS.label),
            "JetBrains Runtime (JBR)",
            "https://github.com/JetBrains/JetBrainsRuntime/releases",
            version,
            logo = Logo.JBR
        )
    }
}
