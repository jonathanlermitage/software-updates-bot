package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK14LinuxChecker : AdoptOpenJDKChecker(14, "linux", "jdk", "x64"), Checker
