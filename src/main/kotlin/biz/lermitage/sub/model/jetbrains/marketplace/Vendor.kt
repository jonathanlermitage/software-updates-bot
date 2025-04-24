package biz.lermitage.sub.model.jetbrains.marketplace

import com.google.gson.annotations.SerializedName

data class Vendor(
    @SerializedName("name") val name: String,
    @SerializedName("isVerified") val isVerified: Boolean
)
