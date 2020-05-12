package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class AdoptOpenJDK11LinuxChecker : AdoptOpenJDKChecker(11, "linux", "jdk", "x64"), Checker
