package com.nexusfalcao.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GptMessageResponse(
    val role: String?,
    val content: String?,
    @SerializedName("tool_calls")
    val toolCalls: List<GptToolCalls>,
)
