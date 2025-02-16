package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class JetBrainsIntelliJChecker : JetBrainsIDEChecker("IntelliJ IDEA", "IC", true, "https://www.jetbrains.com/idea/", Logo.INTELLIJ), Checker
