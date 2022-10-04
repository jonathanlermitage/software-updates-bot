package biz.lermitage.sub.model

import java.util.Date

data class Report(val updates: List<SoftwareUpdate>, val createdAt: Date = Date())
