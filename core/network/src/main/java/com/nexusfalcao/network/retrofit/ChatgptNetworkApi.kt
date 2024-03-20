package com.nexusfalcao.network.retrofit

import com.nexusfalcao.network.model.request.GptRequest
import com.nexusfalcao.network.model.response.GptResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatgptNetworkApi {
    @POST("v1/chat/completions")
    suspend fun createNewRecipe(@Body body: GptRequest): Response<GptResponse>
}