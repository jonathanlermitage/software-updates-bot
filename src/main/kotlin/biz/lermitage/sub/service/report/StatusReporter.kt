package biz.lermitage.sub.service.report

import biz.lermitage.sub.model.DetailedException

interface StatusReporter {

    fun generateError(errors: List<DetailedException>)

    fun generateSuccess()
}
