package com.nexusfalcao.network.model.request

data class GptTool(
    val type: String,
    val function: GptFunction,
)
