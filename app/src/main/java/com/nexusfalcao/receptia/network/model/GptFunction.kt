package com.nexusfalcao.receptia.network.model

import com.google.gson.JsonObject

data class GptFunction(
    val name: String,
    val parameters: JsonObject,
)
