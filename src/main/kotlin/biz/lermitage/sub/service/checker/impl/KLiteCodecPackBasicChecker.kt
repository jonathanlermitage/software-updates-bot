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
 * K-Lite Codec Pack Basic checker.
 */
@Service
class KLiteCodecPackBasicChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://www.codecguide.com/changelogs_basic.htm")
        val titles = body.getElementsByTag("h4")
        val versionTitleFound = titles.toList()
            .find { element: Element ->
                element.text().contains("Changelog ", ignoreCase = true) &&
                    element.text().contains("to", ignoreCase = true) &&
                    element.text().contains("~", ignoreCase = true)
            }
        val version = versionTitleFound!!.text()
            .replace("Changelog", "", ignoreCase = true)
            .trim()

        return SoftwareUpdate(
            listOf(Category.CODECS.label, Category.MS_WINDOWS.label),
            "K-Lite Codec Pack Basic",
            "https://www.codecguide.com/download_k-lite_codec_pack_basic.htm",
            version,
            logo = Logo.KLITE
        )
    }
}
