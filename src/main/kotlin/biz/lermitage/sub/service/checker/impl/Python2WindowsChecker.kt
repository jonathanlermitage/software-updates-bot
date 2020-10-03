package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Python2WindowsChecker : PythonChecker("windows", "windows", "2"), Checker
