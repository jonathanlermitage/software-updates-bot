package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.Globals
import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.model.jetbrains.JetBrainsApiResponse
import biz.lermitage.sub.model.jetbrains.Release
import biz.lermitage.sub.service.report.Reporter
import biz.lermitage.sub.service.scrapper.Scrapper
import com.google.gson.Gson
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class UCReporterImpl(private val conf: LocalAppConf) : Reporter {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var scrapper: Scrapper

    override fun generate(updates: List<SoftwareUpdate>): Boolean {
        generateReport()
        return true
    }

    private fun generateReport() {
        val reportFile = File(conf.reportFile.uc)
        reportFile.parentFile.mkdirs()
        reportFile.delete()
        write(reportFile)
    }

    private fun write(reportFile: File) {
        val lines = ArrayList<String>()

        val platformTypes = listOf("IU", "IC", "RD", "PC", "WS", "PS", "CL", "GO", "RM", "DG", "RS", "QA", "RR")

        platformTypes.forEach { platformType ->
            run {
                val json = scrapper.fetchText(
                    "https://data.services.jetbrains.com/products?code=$platformType"
                )
                val jsonFirstElt = json.substring(1, json.length - 1) // the response is wrapped in an array of one element "[{...}]"; unwrap it
                val apiResponse = Gson().fromJson(jsonFirstElt, JetBrainsApiResponse::class.java)
                val releases = apiResponse.releases

                var ga = releases.firstOrNull { release -> release.type == "release" }
                if (ga == null) {
                    ga = Release(type = "release", version = "", build = "", date = "")
                }
                var eap = releases.firstOrNull { release -> release.type == "eap" }
                if (eap == null) {
                    eap = Release(type = "eap", version = "", build = "", date = "")
                }

                lines.add("# ${apiResponse.name}")
                lines.add("$platformType.release.version=${ga.version}")
                lines.add("$platformType.release.build=${ga.build}")
                lines.add("$platformType.release.date=${ga.date}")
                lines.add("$platformType.eap.version=${eap.version}")
                lines.add("$platformType.eap.build=${eap.build}")
                lines.add("$platformType.eap.date=${eap.date}")
                lines.add("")
            }
        }

        FileUtils.writeLines(reportFile, Globals.reportCharset().name(), lines)
        logger.info("saved UC report file to: ${reportFile.absolutePath}")
    }
}
