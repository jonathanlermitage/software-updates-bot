package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium19MacosChecker : AdoptiumChecker(19, "mac", "jdk", "x64"), Checker
