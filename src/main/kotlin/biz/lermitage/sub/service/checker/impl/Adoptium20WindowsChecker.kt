package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium20WindowsChecker : AdoptiumChecker(20, "windows", "jdk", "x64"), Checker
