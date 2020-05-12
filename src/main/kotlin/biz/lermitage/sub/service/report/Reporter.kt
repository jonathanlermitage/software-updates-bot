package biz.lermitage.sub.service.report

import biz.lermitage.sub.model.SoftwareUpdate

interface Reporter {

    fun generate(updates: List<SoftwareUpdate>): Boolean
}
