package com.nexusfalcao.network.model.request

import com.google.gson.annotations.SerializedName

data class GtpMessage(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String,
)
