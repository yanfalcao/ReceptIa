package com.example.receptia.network

import com.example.receptia.network.model.GptRequest
import com.example.receptia.network.model.GptResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GptApiService {
    @POST("v1/chat/completions")
    suspend fun createChatCompletion(@Body body: GptRequest): Response<GptResponse>
}
