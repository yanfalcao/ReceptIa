package com.nexusfalcao.receptia.network

import com.google.gson.JsonElement
import com.nexusfalcao.receptia.network.model.GptRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GptApiService {
    @POST("v1/chat/completions")
    suspend fun createChatCompletion(@Body body: GptRequest): Response<JsonElement>
}
