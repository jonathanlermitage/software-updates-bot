package biz.lermitage.sub.model.jetbrains

import com.google.gson.annotations.SerializedName

data class Release(
    @SerializedName("type") val type: String,
    @SerializedName("version") val version: String,
    @SerializedName("build") val build: String,
    @SerializedName("date") val date: String
)
