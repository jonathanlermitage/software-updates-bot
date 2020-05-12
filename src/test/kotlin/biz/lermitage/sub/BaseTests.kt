package biz.lermitage.sub

import biz.lermitage.sub.conf.LocalAppConf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseTests {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var conf: LocalAppConf

    @BeforeEach
    fun clearData() {
        listOf(conf.reportFile.json, conf.reportFile.md, conf.reportFile.mdByDate).forEach { filename: String ->
            val file = File(filename)
            logger.info("deleted ${file.absolutePath} before test: ${file.delete()}")
        }
    }
}
