package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Autowired

abstract class GenericJsonPathChecker(
    private val categories: List<String>,
    private val url: String,
    private val jsonpath: String,
    private val name: String,
    private val website: String,
    private val logo: Logo = Logo.NONE
) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val json = scrapper.fetchText(url)
        val version = JsonPath.read<String>(json, jsonpath)

        return SoftwareUpdate(
            categories,
            name,
            website,
            version,
            logo = logo
        )
    }
}
