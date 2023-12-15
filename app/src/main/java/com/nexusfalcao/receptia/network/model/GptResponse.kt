package com.nexusfalcao.receptia.network.model

import com.google.gson.annotations.SerializedName

data class GptResponse(
    @SerializedName("choices")
    val choices: List<GptChoice>,

    @SerializedName("id")
    val id: String,

    @SerializedName("usage")
    val usage: GptUsage
)
