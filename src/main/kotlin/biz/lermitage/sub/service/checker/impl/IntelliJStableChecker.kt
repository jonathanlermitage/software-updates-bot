package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * IntelliJ stable checker.
 */
@Service
class IntelliJStableChecker : IntelliJChecker(true), Checker
