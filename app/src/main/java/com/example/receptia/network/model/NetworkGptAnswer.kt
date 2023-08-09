package com.example.receptia.network.model

import com.google.gson.annotations.SerializedName

data class NetworkGptAnswer(
    @SerializedName("choices")
    val choices: List<NetworkGptChoice>,

    @SerializedName("id")
    val id: String,

    @SerializedName("usage")
    val usage: NetworkGptUsage
)
