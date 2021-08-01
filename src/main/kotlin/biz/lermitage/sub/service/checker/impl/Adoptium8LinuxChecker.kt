package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Adoptium8LinuxChecker : AdoptiumChecker(8, "linux", "jdk", "x64"), Checker
