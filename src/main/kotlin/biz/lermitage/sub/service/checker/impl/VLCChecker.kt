package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * VLC checker.
 */
@Service
class VLCChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://www.videolan.org/vlc/")
        var version = body.getElementsByAttributeValue("id", "downloadDetails")[0].text()
        if (version.contains("•"))
            version = version.substring(0, version.indexOf("•"))
                .toLowerCase()
                .replace("version", "")
                .trim()

        return SoftwareUpdate(
            listOf(Category.VIDEO_PLAYER.label, Category.VLC.label),
            "VLC",
            "https://www.videolan.org/vlc/",
            version,
            logo = Logo.VLC)
    }
}
