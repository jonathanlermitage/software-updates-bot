package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK14MacosChecker : AdoptOpenJDKChecker(14, "mac", "jdk", "x64"), Checker
