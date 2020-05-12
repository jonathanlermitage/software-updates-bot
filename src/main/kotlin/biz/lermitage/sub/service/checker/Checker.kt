package biz.lermitage.sub.service.checker

import biz.lermitage.sub.model.SoftwareUpdate

/**
 * Software updates checker.
 */
interface Checker {

    fun check(): SoftwareUpdate
}
