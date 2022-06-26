package biz.lermitage.sub.model

import java.util.Date

/**
 * Represents a software update.
 * Field "checkDate" is excluded from "equals" and "hashCode" methods.
 */
data class SoftwareUpdate(
    val categories: List<String>,
    val name: String,
    val website: String,
    val version: String,
    val checkDate: Date = Date(),
    // logo will be null when item is loaded from an old json and item has no logo and no update
    // TODO configure GSON deserializer to convert null logo to Logo.NONE
    val logo: Logo? = Logo.NONE) : Comparable<SoftwareUpdate> {

    override fun compareTo(other: SoftwareUpdate): Int {
        return (name + version + website).compareTo(other.name + other.version + other.website)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SoftwareUpdate

        if (categories != other.categories) return false
        if (name != other.name) return false
        if (website != other.website) return false
        if (version != other.version) return false

        return true
    }

    override fun hashCode(): Int {
        var result = categories.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + website.hashCode()
        result = 31 * result + version.hashCode()
        return result
    }

    companion object {
        val comparatorByDateDesc: Comparator<SoftwareUpdate> = kotlin.Comparator { o1, o2 ->
            -(o1.checkDate.compareTo(o2.checkDate))
        }
    }
}
