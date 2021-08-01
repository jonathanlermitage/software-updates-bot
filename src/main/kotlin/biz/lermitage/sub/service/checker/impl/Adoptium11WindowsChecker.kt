package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium11WindowsChecker : AdoptiumChecker(11, "windows", "jdk", "x64"), Checker
