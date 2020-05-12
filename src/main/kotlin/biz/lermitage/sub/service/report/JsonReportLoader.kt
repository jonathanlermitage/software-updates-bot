package biz.lermitage.sub.service.report

import biz.lermitage.sub.model.Report

interface JsonReportLoader {

    /**
     * Load JSON report file.
     * Return an empty report if file doesn't exist.
     */
    fun load(): Report
}
