package com.example.receptia.repository

import com.example.receptia.ReceptIaApplication
import com.example.receptia.network.exceptions.ServerErrorException
import com.example.receptia.network.exceptions.UnseccessfulRequestException
import com.example.receptia.network.model.NetworkGptAnswer
import com.example.receptia.network.model.NetworkGptRequest
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
    suspend fun getPrompt(request: NetworkGptRequest): NetworkGptAnswer {
        try {
            val response = ReceptIaApplication.http.gptService().getPrompt(request)
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