package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium11MacosChecker : AdoptiumChecker(11, "mac", "jdk", "x64"), Checker
