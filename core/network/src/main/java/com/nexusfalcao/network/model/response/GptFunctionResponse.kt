package com.nexusfalcao.network.model.response

import androidx.annotation.Keep

@Keep
data class GptFunctionResponse(
    val name: String,
    val arguments: String,
)