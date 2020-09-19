package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK15LinuxChecker : AdoptOpenJDKChecker(15, "linux", "jdk", "x64"), Checker
