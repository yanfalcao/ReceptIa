package com.nexusfalcao.receptia.repository

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.nexusfalcao.receptia.ReceptIaApplication
import com.nexusfalcao.receptia.network.exceptions.ServerErrorException
import com.nexusfalcao.receptia.network.exceptions.UnseccessfulRequestException
import com.nexusfalcao.receptia.network.model.GptRequest
import javax.inject.Inject

class NetworkRepository @Inject constructor() {
    suspend fun createChatCompletion(request: GptRequest): JsonElement {
        try {
            Gson().toJson(request).toString()
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
