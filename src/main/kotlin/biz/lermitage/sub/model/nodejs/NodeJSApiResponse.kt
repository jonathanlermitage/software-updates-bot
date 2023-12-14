package biz.lermitage.sub.model.nodejs

import com.google.gson.annotations.SerializedName

// thx https://www.json2kotlin.com
data class NodeJSApiResponse(
    @SerializedName("version") val version : String,
    @SerializedName("lts") val lts : String?
)
