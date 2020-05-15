package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Maven checker.
 */
@Service
class MavenChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://maven.apache.org/docs/history.html")
        val version = body.getElementsByTag("table")[0].getElementsByTag("tr")[1].getElementsByTag("td")[1].text()

        return SoftwareUpdate(
            listOf(Category.JAVA.label, Category.MAVEN.label),
            "Maven",
            "https://maven.apache.org/download.cgi",
            version)
    }
}
