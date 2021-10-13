package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired

/**
 * MariaDB checker.
 * @param versionLeftPart "major.minor" part of version, without the ".patch" part
 */
abstract class MariaDBChecker(private val versionLeftPart: String) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://downloads.mariadb.org/mariadb/+releases/")
        val versionLnkInHistoryTable = body.getElementsByAttributeValueStarting("href", "/mariadb/$versionLeftPart.")[0]
        val versionNumber = versionLnkInHistoryTable.text()
        val versionType = versionLnkInHistoryTable.parent()!!.parent()!!.getElementsByTag("td")[2].text()
        val version = if (versionType.lowercase().contains("stable")) {
            versionNumber
        } else {
            "$versionNumber $versionType"
        }

        return SoftwareUpdate(
            listOf(Category.DATABASE.label, Category.MARIADB.label),
            "MariaDB $versionLeftPart",
            "https://downloads.mariadb.org",
            version,
            logo = Logo.MARIADB)
    }
}
