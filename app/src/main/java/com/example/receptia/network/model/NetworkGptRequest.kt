package com.example.receptia.network.model

data class NetworkGptRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<NetworkGtpMessage>,
)
