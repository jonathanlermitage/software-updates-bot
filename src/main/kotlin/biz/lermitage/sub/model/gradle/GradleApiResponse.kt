package biz.lermitage.sub.model.gradle

import com.google.gson.annotations.SerializedName

data class GradleApiResponse(
    @SerializedName("finalReleases") val finalReleases: List<FinalReleases>
)
