package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.model.jetbrains.JetBrainsApiResponse
import biz.lermitage.sub.model.jetbrains.Release
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * IntelliJ checker.
 * @param ideName the name of the IDE
 * @param platformType the platform type identifier, per example: IC, IU, RD, PC...
 * @param isStable true for stable IDE, false for EAP IDE
 * @param websiteUrl the website URL of the IDE
 */
abstract class JetBrainsIDEChecker(
    private val ideName: String,
    private val platformType: String,
    private val isStable: Boolean,
    private val websiteUrl: String = "",
    private val logo: Logo = Logo.NONE
) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val release = findLatestIdeaVersion(isStable)

        return SoftwareUpdate(
            categories = listOf(Category.JAVA.label, Category.JETBRAINS.label),
            name = ideName,
            website = websiteUrl,
            version = "${release.version} (${release.build}) ~ ${release.date}",
            logo = logo
        )
    }

    private fun findLatestIdeaVersion(isStable: Boolean): Release {

        /** Read a remote file as String. */
        fun readRemoteContent(url: URL): String {
            val content = StringBuilder()
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            BufferedReader(InputStreamReader(conn.inputStream)).use { rd ->
                var line: String? = rd.readLine()
                while (line != null) {
                    content.append(line)
                    line = rd.readLine()
                }
            }
            return content.toString()
        }

        /** Find the latest IntelliJ version from the given url and xpath expression that picks the desired IDE version and channel. */
        val json = scrapper.fetchText(
            "https://data.services.jetbrains.com/products?code=$platformType"
        )
        val jsonFirstElt = json.substring(1, json.length - 1) // the response is wrapped in an array of one element "[{...}]"; unwrap it
        val apiResponse = Gson().fromJson(jsonFirstElt, JetBrainsApiResponse::class.java)
        val releases = apiResponse.releases

        return releases.first { release -> release.type == (if (isStable) "release" else "eap") }
    }
}
