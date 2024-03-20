package com.nexusfalcao.network.model.request

import com.google.gson.annotations.SerializedName

data class GptRequest(
    val model: String,
    val messages: List<GtpMessage>,
    val tools: List<GptTool>,
    @SerializedName("tool_choice")
    val toolChoice: String
)
