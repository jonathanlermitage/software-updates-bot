package biz.lermitage.sub.service.dreport.impl

import biz.lermitage.sub.conf.LocalAppConf
import biz.lermitage.sub.service.dreport.DReporter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JetBrainsPluginsJetBrainsReporterImpl : AbstractJetBrainsPluginsReporter(vendor = "JetBrains s.r.o."), DReporter {

    @Autowired
    lateinit var conf: LocalAppConf

    override fun getReportFilePath(): String {
        return conf.reportFile.jbMarketplacePluginsLatestJetbrains
    }
}
