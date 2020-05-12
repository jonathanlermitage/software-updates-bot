package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK8LinuxChecker : AdoptOpenJDKChecker(8, "linux", "jdk", "x64"), Checker
