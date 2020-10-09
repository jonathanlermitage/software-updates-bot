package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired

/**
 * Python checker.
 * @param platformId platform identifier in url.
 * @param platformName platform name in report.
 * @param majorVersion Python major version: 2 or 3.
 */
abstract class PythonChecker(private val platformId: String,
                             private val platformName: String,
                             private val majorVersion: String) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://www.python.org/downloads/$platformId/")
        val versionPrefix = "latest python $majorVersion release - python"
        val version = body.getElementsByAttributeValueStarting("href", "/downloads")
            .stream()
            .filter { it.text().toLowerCase().startsWith(versionPrefix) }
            .findFirst()
            .get().text().substring(versionPrefix.length)

        return SoftwareUpdate(
            listOf(Category.PYTHON.label),
            "Python $majorVersion $platformName",
            "https://www.python.org/downloads/$platformId/",
            version)
    }
}
