package biz.lermitage.sub.model.jetbrains.marketplace

import com.google.gson.annotations.SerializedName

@Suppress("SpellCheckingInspection")
data class Plugin(
    @SerializedName("id") val id: String,
    @SerializedName("xmlId\t") val xmlId	: String,
    @SerializedName("link") val link: String,
    @SerializedName("name") val name: String,
    @SerializedName("preview") val preview: String,
    @SerializedName("downloads") val downloads: Integer,
    @SerializedName("pricingModel") val pricingModel: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("cdate") val cdate: String,
    @SerializedName("hasSource") val hasSource: String,
    @SerializedName("vendor") val vendor: Vendor
)
