package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium16WindowsChecker : AdoptiumChecker(16, "windows", "jdk", "x64"), Checker
