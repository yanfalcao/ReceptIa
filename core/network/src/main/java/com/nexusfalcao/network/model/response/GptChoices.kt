package com.nexusfalcao.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GptChoices(
    val index: Int,
    @SerializedName("message")
    val messageResponse: GptMessageResponse,
    val logprobs: String?,
    @SerializedName("finish_reason")
    val finishReason: String?,
)
