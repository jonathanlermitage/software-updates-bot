package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK15WindowsChecker : AdoptOpenJDKChecker(15, "windows", "jdk", "x64"), Checker
