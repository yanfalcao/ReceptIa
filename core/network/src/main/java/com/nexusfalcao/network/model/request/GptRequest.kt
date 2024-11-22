package com.nexusfalcao.network.model.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GptRequest(
    @Keep
    @SerializedName("model")
    val model: String,
    @Keep
    @SerializedName("messages")
    val messages: List<GtpMessage>,
    @Keep
    @SerializedName("tools")
    val tools: List<GptTool>,
    @Keep
    @SerializedName("tool_choice")
    val toolChoice: String,
)
