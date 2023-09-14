package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium21LinuxChecker : AdoptiumChecker(21, "linux", "jdk", "x64"), Checker
