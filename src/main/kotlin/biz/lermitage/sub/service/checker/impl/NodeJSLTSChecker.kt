package biz.lermitage.sub.service.checker.impl

import biz.lermitage.sub.model.nodejs.NodeJSType
import biz.lermitage.sub.service.checker.Checker
import org.springframework.stereotype.Service

/**
 * NodeJS LTS update checker.
 */
@Service
class NodeJSLTSChecker : NodeJSChecker(NodeJSType.LTS), Checker
