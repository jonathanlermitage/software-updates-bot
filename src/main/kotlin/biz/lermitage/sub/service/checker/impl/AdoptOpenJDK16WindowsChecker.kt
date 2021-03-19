package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK16WindowsChecker : AdoptOpenJDKChecker(16, "windows", "jdk", "x64"), Checker
