package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 7-Zip checker.
 */
@Service
class SevenZipChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchText("https://www.7-zip.org/history.txt")
        val versionRegex = Regex("^(\\d+(?:\\.\\d+)+)\\s+\\d{4}-\\d{2}-\\d{2}", RegexOption.MULTILINE)
        val version = versionRegex.find(body)!!.groupValues[1]

        return SoftwareUpdate(
            listOf(Category.MS_WINDOWS.label, Category.ARCHIVER.label),
            "7-Zip",
            "https://www.7-zip.org",
            version,
            logo = Logo.SEVEN_ZIP
        )
    }
}
