package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * TechPowerUp checker.
 */
abstract class TechPowerUpChecker(
    private val productName: String,
    private val url: String,
    private val categories: List<String>,
    private val logo: Logo
) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun check(): SoftwareUpdate {
        val body = scrapper.fetchHtml(url, executeJS = true)

        val titles = body.getElementsByTag("h3")
        logger.info("\n\n---\n\n")
        logger.info(scrapper.fetchText(url))
        logger.info("\n\n---\n\n")
        val versionTitleFound = titles.toList()
            .find { element: Element ->
                element.className().contains("title")
                    && element.text().contains(productName, ignoreCase = true)
            }
        val version = versionTitleFound!!.text()
            .replace(productName, "", ignoreCase = true)
            .trim()

        return SoftwareUpdate(
            categories,
            productName,
            url,
            version,
            logo = logo
        )
    }
}
