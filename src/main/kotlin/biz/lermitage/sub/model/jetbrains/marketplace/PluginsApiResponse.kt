package biz.lermitage.sub.model.jetbrains.marketplace

import com.google.gson.annotations.SerializedName

data class PluginsApiResponse(
    @SerializedName("plugins") val plugins: List<Plugin>
)
