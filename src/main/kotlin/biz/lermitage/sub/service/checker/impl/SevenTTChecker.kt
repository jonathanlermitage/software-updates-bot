package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 7+ Taskbar Tweaker checker.
 */
@Service
class SevenTTChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://rammichael.com/downloads/7tt_setup.exe?changelog")
        val version = body.getElementsByTag("b")[0].text().trim()

        return SoftwareUpdate(
            listOf(Category.MS_WINDOWS.label),
            "7+ Taskbar Tweaker",
            "https://rammichael.com/7-taskbar-tweaker",
            version)
    }
}
