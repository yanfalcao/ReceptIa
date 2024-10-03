package com.nexusfalcao.network.model.request

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class GptFunction(
    @SerializedName("name")
    val name: String,
    @SerializedName("parameters")
    val parameters: JsonObject,
)
