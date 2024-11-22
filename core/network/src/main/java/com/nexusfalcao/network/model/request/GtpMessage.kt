package com.nexusfalcao.network.model.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GtpMessage(
    @Keep
    @SerializedName("role")
    val role: String,
    @Keep
    @SerializedName("content")
    val content: String,
)
