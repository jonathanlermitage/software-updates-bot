package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.model.adoptopenjdk.AdoptOpenJdkApiResponse
import biz.lermitage.sub.service.checker.Checker
import com.google.gson.Gson
import org.jsoup.Jsoup

/**
 * AdoptOpenJDK JDK update checker.
 * @param major JDK major version number: 8, 11, 14...
 * @param os OS name: "windows", "linux", "mac", "solaris"...
 * @param imageType JDK distribution type: "jdk" or "jre"
 * @param architecture JDK Architecture: "x64" (64-bits) or "x86" (32-bits)
 */
abstract class AdoptOpenJDKChecker(
    private val major: Int,
    private val os: String,
    private val imageType: String,
    private val architecture: String) : Checker {

    override fun check(): SoftwareUpdate {
        val json = Jsoup.connect("https://api.adoptopenjdk.net/v3/assets/latest/$major/hotspot?release=latest&jvm_impl=hotspot&vendor=adoptopenjdk&")
            .ignoreContentType(Globals.SCRAPPER_IGNORE_CONTENT_TYPE)
            .followRedirects(Globals.SCRAPPER_FOLLOW_REDIRECTS)
            .userAgent(Globals.SCRAPPER_USER_AGENT)
            .get().text()
        val apiResponse = Gson().fromJson(json, Array<AdoptOpenJdkApiResponse>::class.java)
        val version = apiResponse.toList().find { apiResponseElt: AdoptOpenJdkApiResponse ->
            apiResponseElt.binary.os.equals(os, true)
                && apiResponseElt.binary.image_type.equals(imageType, true)
                && apiResponseElt.binary.architecture.equals(architecture, true)
        }!!.release_name

        return SoftwareUpdate(
            "AdoptOpenJDK $imageType$major $os $architecture",
            "https://adoptopenjdk.net/releases.html?variant=openjdk$major&jvmVariant=hotspot",
            version)
    }
}
