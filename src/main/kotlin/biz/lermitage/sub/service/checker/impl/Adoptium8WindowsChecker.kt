package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium8WindowsChecker : AdoptiumChecker(8, "windows", "jdk", "x64"), Checker
