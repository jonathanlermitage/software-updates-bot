package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium18WindowsChecker : AdoptiumChecker(18, "windows", "jdk", "x64"), Checker
