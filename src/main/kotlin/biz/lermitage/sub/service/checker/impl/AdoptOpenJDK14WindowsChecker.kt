package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK14WindowsChecker : AdoptOpenJDKChecker(14, "windows", "jdk", "x64"), Checker
