package biz.lermitage.sub.model.jetbrains

import com.google.gson.annotations.SerializedName

data class JetBrainsApiResponse(
    @SerializedName("releases") val releases: List<Release>,
    @SerializedName("name") val name: String
)
