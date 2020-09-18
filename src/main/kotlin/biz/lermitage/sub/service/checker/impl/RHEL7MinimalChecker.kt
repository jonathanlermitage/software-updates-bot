package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import com.jayway.jsonpath.JsonPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * RHEL7Minimal checker.
 */
@Service
class RHEL7MinimalChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val url = "https://catalog.redhat.com/api/containers/v1/repositories/registry/registry.access.redhat.com/repository/rhel7-minimal/images?exclude=data.repositories.comparison.advisory_rpm_mapping,data.brew,data.cpe_ids,data.top_layer_id,data.freshness_grades,data.repositories&page_size=500&page=0"
        val json = scrapper.fetchText(url)
        val jsonpath = "\$.data[0].parsed_data.labels[14]"
        val urlVersion = JsonPath.read<LinkedHashMap<String, String>>(json, jsonpath).getValue("value")
        val version = urlVersion.substring(urlVersion.lastIndexOf("/")).replace("/", "")

        return SoftwareUpdate(
            listOf(Category.OS.label, Category.RHEL.label),
            "RHEL7Minimal",
            "https://access.redhat.com/products/red-hat-enterprise-linux",
            version)
    }
}
