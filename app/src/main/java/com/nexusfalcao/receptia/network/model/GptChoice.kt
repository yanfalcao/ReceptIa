package com.nexusfalcao.receptia.network.model

import com.google.gson.annotations.SerializedName

data class GptChoice(
    @SerializedName("finish_reason")
    val finish_reason: String,

    @SerializedName("index")
    val index: Int,

    @SerializedName("message")
    val message: GptMessageResponse

)
