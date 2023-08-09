package com.example.receptia.repository

import com.example.receptia.network.GptApiService
import com.example.receptia.network.model.NetworkGptAnswer
import com.example.receptia.network.model.NetworkGptRequest
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val apiService: GptApiService) {
    suspend fun getPrompt(request: NetworkGptRequest): NetworkGptAnswer {
        return apiService.getPrompt(request)
    }
}
