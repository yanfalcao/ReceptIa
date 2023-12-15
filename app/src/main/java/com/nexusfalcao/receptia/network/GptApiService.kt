package com.nexusfalcao.receptia.network

import com.nexusfalcao.receptia.network.model.GptRequest
import com.nexusfalcao.receptia.network.model.GptResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GptApiService {
    @POST("v1/chat/completions")
    suspend fun createChatCompletion(@Body body: GptRequest): Response<GptResponse>
}
