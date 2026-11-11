package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Windhawk checker.
 */
@Service
class WindhawkChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchText("https://github.com/ramensoftware/windhawk/releases.atom")
        val titleRegex = Regex("<entry>.*?<title>(.*?)</title>", RegexOption.DOT_MATCHES_ALL)
        val version = titleRegex.find(body)!!.groupValues[1].trim()

        return SoftwareUpdate(
            listOf(Category.MS_WINDOWS.label),
            "Windhawk",
            "https://github.com/ramensoftware/windhawk/releases",
            version,
            logo = Logo.WINDHAWK
        )
    }
}
