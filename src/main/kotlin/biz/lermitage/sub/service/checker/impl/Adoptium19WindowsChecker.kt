package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium19WindowsChecker : AdoptiumChecker(19, "windows", "jdk", "x64"), Checker
