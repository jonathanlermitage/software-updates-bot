package biz.lermitage.sub.model.adoptopenjdk

import com.google.gson.annotations.SerializedName

// thx https://www.json2kotlin.com
data class AdoptOpenJdkApiResponse(
    @SerializedName("binary") val binary: Binary,
    @SerializedName("release_name") val releaseName: String
)
