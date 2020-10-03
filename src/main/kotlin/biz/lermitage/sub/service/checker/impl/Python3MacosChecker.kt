package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Python3MacosChecker : PythonChecker("mac-osx", "mac", "3"), Checker
