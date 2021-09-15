package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium17WindowsChecker : AdoptiumChecker(17, "windows", "jdk", "x64"), Checker
