package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Autowired

/**
 * MariaDB checker.
 * @param versionLeftPart "major.minor" part of version, without the ".patch" part
 */
abstract class MariaDBChecker(private val versionLeftPart: String) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val json = scrapper.fetchText(
            "https://downloads.mariadb.org/rest-api/mariadb/$versionLeftPart/"
        )
        val jsonpath = "\$['releases'][*]['release_id']"
        val versions = JsonPath.read<ArrayList<String>>(json, jsonpath)
        val version = versions[0]

        return SoftwareUpdate(
            listOf(Category.DATABASE.label, Category.MARIADB.label),
            "MariaDB $versionLeftPart",
            "https://downloads.mariadb.org",
            version,
            logo = Logo.MARIADB
        )
    }
}
