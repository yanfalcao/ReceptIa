package com.nexusfalcao.receptia.network.model

import com.google.gson.annotations.SerializedName

data class GptMessageResponse(
    @SerializedName("tool_calls")
    val functionCall: GptFunctionCallResponse,
)
