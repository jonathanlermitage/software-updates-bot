package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Les Cast Codeurs podcast checker.
 */
@Service
class LesCastCodeursChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml("https://lescastcodeurs.com")
        val version = body.getElementsByClass("card-title")[0].text()

        return SoftwareUpdate(
            listOf(Category.PODCAST.label),
            "Les Cast Codeurs (French podcast)",
            "https://lescastcodeurs.com",
            version)
    }
}
