package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Category
import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.checker.Checker
import biz.lermitage.sub.service.scrapper.Scrapper
import org.springframework.beans.factory.annotation.Autowired
import org.w3c.dom.Document
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

/**
 * IntelliJ checker.
 * @param isStable true for stable IDE, false for EAP IDE
 */
abstract class IntelliJChecker(private val isStable: Boolean) : Checker {

    @Autowired
    lateinit var scrapper: Scrapper

    override fun check(): SoftwareUpdate {
        val version = findLatestIdeaVersion(isStable)

        return SoftwareUpdate(
            listOf(Category.JAVA.label, Category.JETBRAINS.label),
            if (isStable) "IntelliJ IDEA" else "IntelliJ IDEA EAP",
            if (isStable) "https://www.jetbrains.com/idea/" else "https://www.jetbrains.com/idea/nextversion/",
            version,
            logo = if (isStable) Logo.INTELLIJ else Logo.INTELLIJ_EAP
        )
    }

    private fun findLatestIdeaVersion(isStable: Boolean): String {

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
        fun getOnlineLatestIdeVersion(definitionsUrl: URL, xpathExpression: String): String {
            val definitionsStr = readRemoteContent(definitionsUrl)
            val builderFactory = DocumentBuilderFactory.newInstance()
            val builder = builderFactory.newDocumentBuilder()
            val xmlDocument: Document = builder.parse(ByteArrayInputStream(definitionsStr.toByteArray()))
            val xPath = XPathFactory.newInstance().newXPath()
            return xPath.compile(xpathExpression).evaluate(xmlDocument, XPathConstants.STRING) as String
        }

        val definitionsUrl = URL("https://www.jetbrains.com/updates/updates.xml") // IMPORTANT if not available, migrate to https://data.services.jetbrains.com/products?code=IC
        val xpathExpression =
            if (isStable) "/products/product[@name='IntelliJ IDEA']/channel[@id='IC-IU-RELEASE-licensing-RELEASE']/build[1]/@version" else "/products/product[@name='IntelliJ IDEA']/channel[@id='IC-IU-EAP-licensing-EAP']/build[1]/@fullNumber"
        return getOnlineLatestIdeVersion(definitionsUrl, xpathExpression)
    }
}
