package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK15MacosChecker : AdoptOpenJDKChecker(15, "mac", "jdk", "x64"), Checker
