package com.example.receptia.network.model

import com.google.gson.annotations.SerializedName

data class GptRequest(
    val model: String = "gpt-3.5-turbo-1106",
    val messages: List<GtpMessage>,
    val functions: List<GptFunctions>,
    @SerializedName("function_call")
    val functionCall: GptFuncitonCallRequest
)
