package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.model.nodejs.NodeJSApiResponse
import biz.lermitage.sub.model.nodejs.NodeJSType
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired

/**
 * NodeJS update checker.
 * @param type NodeJS release type
 */
abstract class NodeJSChecker(
    private val type: NodeJSType,
) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val json = scrapper.fetchText("https://nodejs.org/download/release/index.json")
        val apiResponse = Gson().fromJson(json, Array<NodeJSApiResponse>::class.java)
        val versions = apiResponse.toList()
        val version = if (type == NodeJSType.CURRENT) {
            versions[0].version
        } else {
            versions
                .filter { nodeJSApiResponse: NodeJSApiResponse -> nodeJSApiResponse.lts != null }
                .filter { nodeJSApiResponse: NodeJSApiResponse -> nodeJSApiResponse.lts!!.lowercase() == "iron" }[0]
                .version
        }

        return SoftwareUpdate(
            listOf(Category.NODEJS.label),
            "NodeJS ${type.label}",
            "https://nodejs.org",
            version.replace("v", ""),
            logo = Logo.NODEJS
        )
    }
}
