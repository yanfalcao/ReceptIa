package com.nexusfalcao.receptia.network.model

import com.google.gson.annotations.SerializedName

data class GptRequest(
    val model: String,
    val messages: List<GtpMessage>,
    val tools: List<GptFunctions>,
    @SerializedName("tool_choice")
    val toolChoice: String
)
