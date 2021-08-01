package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium16LinuxChecker : AdoptiumChecker(16, "linux", "jdk", "x64"), Checker
