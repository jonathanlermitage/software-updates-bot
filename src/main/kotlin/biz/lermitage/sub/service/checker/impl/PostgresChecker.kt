package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired

/**
 * PostgreSQL checker.
 * @param majorVersion "major" part of version
 */
abstract class PostgresChecker(private val majorVersion: String) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://www.postgresql.org/ftp/source/")
        val version = body.getElementsByAttributeValueStarting("href", "v$majorVersion.")[0].attr("href")
            .replace("v", "").replace("/", "")

        return SoftwareUpdate(
            listOf(Category.DATABASE.label, Category.POSTGRESQL.label),
            "PostgreSQL $majorVersion",
            "https://www.postgresql.org/download/",
            version,
            logo = Logo.POSTGRESQL
        )
    }
}
