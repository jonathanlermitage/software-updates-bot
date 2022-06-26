package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium18LinuxChecker : AdoptiumChecker(18, "linux", "jdk", "x64"), Checker
