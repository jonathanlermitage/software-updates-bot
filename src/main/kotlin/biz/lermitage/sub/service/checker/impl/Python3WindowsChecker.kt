package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Python3WindowsChecker : PythonChecker("windows", "windows", "3"), Checker
