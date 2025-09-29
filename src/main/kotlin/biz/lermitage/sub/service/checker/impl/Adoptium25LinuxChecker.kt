package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium25LinuxChecker : AdoptiumChecker(25, "linux", "jdk", "x64"), Checker
