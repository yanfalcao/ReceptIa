package com.nexusfalcao.network.model.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GptTool(
    @Keep
    @SerializedName("type")
    val type: String,
    @Keep
    @SerializedName("function")
    val function: GptFunction,
)
