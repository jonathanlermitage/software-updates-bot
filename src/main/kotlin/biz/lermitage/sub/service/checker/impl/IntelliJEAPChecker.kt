package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * IntelliJ EAP checker.
 */
@Service
class IntelliJEAPChecker : IntelliJChecker(false), Checker
