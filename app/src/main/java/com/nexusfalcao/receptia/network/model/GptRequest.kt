package com.nexusfalcao.receptia.network.model

import com.google.gson.annotations.SerializedName

data class GptRequest(
    val model: String,
    val messages: List<GtpMessage>,
    val functions: List<GptFunctions>,
    @SerializedName("function_call")
    val functionCall: GptFuncitonCallRequest
)
