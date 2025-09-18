package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.Logo
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class JetBrainsIntelliJEAPChecker : JetBrainsIDEChecker("IntelliJ IDEA EAP", "IU", false, "https://www.jetbrains.com/idea/nextversion/", Logo.INTELLIJ_EAP), Checker
