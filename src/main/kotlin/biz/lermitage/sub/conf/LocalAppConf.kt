package biz.lermitage.sub.conf

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("localapp")
class LocalAppConf {

    val reportFile = ReportFile()

    class ReportFile {
        lateinit var json: String
        lateinit var md: String
        lateinit var mdByDate: String
        lateinit var rss: String
        lateinit var atom: String
    }
}
