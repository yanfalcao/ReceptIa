package com.example.receptia.network.model

import com.google.gson.JsonObject

data class GptFunctions(
    val name: String,
    val parameters: JsonObject,
)
