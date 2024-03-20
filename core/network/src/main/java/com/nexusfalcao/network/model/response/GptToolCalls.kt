package com.nexusfalcao.network.model.response

import androidx.annotation.Keep

@Keep
data class GptToolCalls(
    val id: String?,
    val type: String?,
    val function: GptFunctionResponse,
)
