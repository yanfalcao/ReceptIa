package com.nexusfalcao.network.model.request

import com.google.gson.JsonObject

data class GptFunction(
    val name: String,
    val parameters: JsonObject,
)
