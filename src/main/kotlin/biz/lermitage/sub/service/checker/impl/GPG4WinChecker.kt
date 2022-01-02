package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 7+ Taskbar Tweaker checker.
 */
@Service
class GPG4WinChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://www.gpg4win.org/get-gpg4win.html")
        val version = body.getElementsByTag("h2")[0].text().lowercase()
            .replace("download", "")
            .replace("gpg4win ", "").trim()

        return SoftwareUpdate(
            listOf(Category.MS_WINDOWS.label, Category.GPG.label),
            "GPG4Win",
            "https://www.gpg4win.org/get-gpg4win.html",
            version,
            logo = Logo.GPG4WIN)
    }
}
