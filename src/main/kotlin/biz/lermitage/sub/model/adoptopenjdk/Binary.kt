package biz.lermitage.sub.model.adoptopenjdk

import com.google.gson.annotations.SerializedName

// thx https://www.json2kotlin.com
data class Binary(
    @SerializedName("architecture") val architecture: String,
    @SerializedName("image_type") val imageType: String,
    @SerializedName("os") val os: String
)
