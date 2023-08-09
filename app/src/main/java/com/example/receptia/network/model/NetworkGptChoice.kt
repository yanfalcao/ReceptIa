package com.example.receptia.network.model

import com.google.gson.annotations.SerializedName

data class NetworkGptChoice(
    @SerializedName("finish_reason")
    val finish_reason: String,

    @SerializedName("index")
    val index: Int,

    @SerializedName("message")
    val message: NetworkGtpMessage

)
