package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.model.adoptopenjdk.AdoptOpenJdkApiResponse
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired

/**
 * Adoptium JDK update checker.
 * @param major JDK major version number: 8, 11...
 * @param os OS name: "windows", "linux", "mac", "solaris"...
 * @param imageType JDK distribution type: "jdk" or "jre"
 * @param architecture JDK Architecture: "x64" (64-bits) or "x86" (32-bits)
 */
abstract class AdoptiumChecker(
    private val major: Int,
    private val os: String,
    private val imageType: String,
    private val architecture: String
) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val json = scrapper.fetchText(
            "https://api.adoptium.net/v3/assets/latest/$major/hotspot?release=latest&jvm_impl=hotspot&vendor=adoptium&"
        )
        val apiResponse = Gson().fromJson(json, Array<AdoptOpenJdkApiResponse>::class.java)
        val version = apiResponse.toList().find { apiResponseElt: AdoptOpenJdkApiResponse ->
            apiResponseElt.binary.os.equals(os, true)
                && apiResponseElt.binary.imageType.equals(imageType, true)
                && apiResponseElt.binary.architecture.equals(architecture, true)
        }!!.releaseName

        return SoftwareUpdate(
            listOf(Category.JAVA.label, Category.JDK.label, Category.ADOPTIUM.label, Category.ADOPT_OPEN_JDK.label),
            "Adoptium $imageType$major $os $architecture",
            "https://adoptium.net/releases.html?variant=openjdk$major&jvmVariant=hotspot",
            version,
            logo = Logo.ADOPTIUM
        )
    }
}
