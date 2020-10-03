package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Python3SrcChecker : PythonChecker("source", "source", "3"), Checker
