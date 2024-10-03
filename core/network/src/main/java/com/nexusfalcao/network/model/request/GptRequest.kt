package com.nexusfalcao.network.model.request

import com.google.gson.annotations.SerializedName

data class GptRequest(
    @SerializedName("model")
    val model: String,
    @SerializedName("messages")
    val messages: List<GtpMessage>,
    @SerializedName("tools")
    val tools: List<GptTool>,
    @SerializedName("tool_choice")
    val toolChoice: String,
)
