package biz.lermitage.sub.service.report.impl

import biz.lermitage.sub.model.SoftwareUpdate
import biz.lermitage.sub.service.report.UpdatesMerger
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList

@Service
class UpdatesMergerImpl : UpdatesMerger {

    override fun merge(previousUpdates: List<SoftwareUpdate>, currentUpdades: List<SoftwareUpdate>): ArrayList<SoftwareUpdate> {
        val mergedReportSoftwareUpdates = ArrayList<SoftwareUpdate>()

        currentUpdades.forEach(Consumer { currSu: SoftwareUpdate ->
            val dateToKeep: Date
            val prevSoftwareVersion = previousUpdates.find { prevSu: SoftwareUpdate -> prevSu.name == currSu.name }
            dateToKeep = if (prevSoftwareVersion != null && prevSoftwareVersion.version == currSu.version) {
                prevSoftwareVersion.checkDate
            } else {
                currSu.checkDate
            }
            mergedReportSoftwareUpdates.add(SoftwareUpdate(currSu.categories, currSu.name, currSu.website, currSu.version, dateToKeep))
        })

        previousUpdates.forEach(Consumer { prevSu: SoftwareUpdate ->
            if (mergedReportSoftwareUpdates.find { mergedSu: SoftwareUpdate -> prevSu.name == mergedSu.name } == null) {
                mergedReportSoftwareUpdates.add(prevSu)
            }
        })

        return mergedReportSoftwareUpdates
    }
}
