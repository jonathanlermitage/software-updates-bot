package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * VeraCrypt checker.
 */
@Service
class VeraCryptChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://github.com/veracrypt/VeraCrypt/releases/latest", executeJS = true)
        val version = body.getElementsByTag("h1")[3].text().replace("VeraCrypt version", "", ignoreCase = true).trim()

        return SoftwareUpdate(
            listOf(Category.VERACRYPT.label),
            "VeraCrypt",
            "https://www.veracrypt.fr/en/Downloads.html",
            version,
            logo = Logo.VERACRYPT
        )
    }
}
