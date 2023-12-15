package com.nexusfalcao.receptia.network.model

import com.google.gson.annotations.SerializedName

data class GptMessageResponse(
    @SerializedName("function_call")
    val functionCall: GptFunctionCallResponse,
)
