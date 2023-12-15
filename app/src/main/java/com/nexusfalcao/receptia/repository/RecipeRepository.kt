package com.nexusfalcao.receptia.repository

import com.nexusfalcao.receptia.ReceptIaApplication
import com.nexusfalcao.receptia.network.exceptions.ServerErrorException
import com.nexusfalcao.receptia.network.exceptions.UnseccessfulRequestException
import com.nexusfalcao.receptia.network.model.GptRequest
import com.nexusfalcao.receptia.network.model.GptResponse
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
    suspend fun createChatCompletion(request: GptRequest): GptResponse {
        try {
            val response = ReceptIaApplication.instance
                .http
                .gptService()
                .createChatCompletion(request)

            when (response.code()) {
                in 200..299 -> {
                    return response.body()
                        ?: throw UnseccessfulRequestException("Unsuccessful notwork request")
                }

                in 500..599 -> {
                    throw ServerErrorException("Server error on network request")
                }

                else -> {
                    throw UnseccessfulRequestException("Unsuccessful notwork request")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
