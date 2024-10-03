package com.nexusfalcao.network.model.request

import com.google.gson.annotations.SerializedName

data class GptTool(
    @SerializedName("type")
    val type: String,
    @SerializedName("function")
    val function: GptFunction,
)
