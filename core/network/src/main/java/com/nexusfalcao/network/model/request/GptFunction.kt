package com.nexusfalcao.network.model.request

import androidx.annotation.Keep
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

@Keep
data class GptFunction(
    @Keep
    @SerializedName("name")
    val name: String,
    @Keep
    @SerializedName("parameters")
    val parameters: JsonObject,
)
