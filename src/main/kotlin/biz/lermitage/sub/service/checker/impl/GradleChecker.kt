package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.model.gradle.GradleApiResponse
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Gradle checker.
 */
@Service
class GradleChecker : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val json = scrapper.fetchText(
            "https://raw.githubusercontent.com/gradle/gradle/master/released-versions.json")
        val apiResponse = Gson().fromJson(json, GradleApiResponse::class.java)
        val version = apiResponse.finalReleases[0].version

        return SoftwareUpdate(
            listOf(Category.JAVA.label, Category.GRADLE.label),
            "Gradle",
            "https://gradle.org/releases/",
            version)
    }
}
