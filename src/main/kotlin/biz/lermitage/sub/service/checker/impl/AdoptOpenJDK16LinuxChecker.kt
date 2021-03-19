package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK16LinuxChecker : AdoptOpenJDKChecker(16, "linux", "jdk", "x64"), Checker
