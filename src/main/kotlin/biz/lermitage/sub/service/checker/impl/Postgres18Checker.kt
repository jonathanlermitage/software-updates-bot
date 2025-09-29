package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

@Service
class Postgres18Checker : PostgresChecker("18"), Checker
