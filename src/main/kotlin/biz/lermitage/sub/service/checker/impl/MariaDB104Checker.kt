package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class MariaDB104Checker : MariaDBChecker("10.4"), Checker
