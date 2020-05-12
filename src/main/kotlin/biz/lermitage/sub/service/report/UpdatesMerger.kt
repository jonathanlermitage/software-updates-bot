package biz.lermitage.sub.service.report

import biz.lermitage.sub.model.SoftwareUpdate

interface UpdatesMerger {

    /**
     * Merge given lists of updates. Most recent updates are kept in returned list.
     */
    fun merge(previousUpdates: List<SoftwareUpdate>, currentUpdades: List<SoftwareUpdate>): ArrayList<SoftwareUpdate>
}
