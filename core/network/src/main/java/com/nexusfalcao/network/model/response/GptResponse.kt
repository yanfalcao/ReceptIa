package com.nexusfalcao.network.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GptResponse(
    val id: String?,
    @SerializedName("object")
    val typeRequest: String?,
    val usage: GptUsageTokens,
    val created: Int,
    val model: String,
    val choices: List<GptChoices>,
    @SerializedName("system_fingerprint")
    val systemFingerprint: String?
)
