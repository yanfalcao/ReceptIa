package com.example.receptia.network

import com.example.receptia.network.model.NetworkGptAnswer
import com.example.receptia.network.model.NetworkGptRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GptApiService {
    @POST("v1/chat/completions")
    suspend fun getPrompt(@Body body: NetworkGptRequest): Response<NetworkGptAnswer>
}
