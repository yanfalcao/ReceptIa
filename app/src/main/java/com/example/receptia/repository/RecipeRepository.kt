package com.example.receptia.repository

import com.example.receptia.ReceptIaApplication
import com.example.receptia.network.model.NetworkGptAnswer
import com.example.receptia.network.model.NetworkGptRequest
import javax.inject.Inject

class RecipeRepository @Inject constructor() {
    suspend fun getPrompt(request: NetworkGptRequest): NetworkGptAnswer {
        return ReceptIaApplication.http.gptApiService().getPrompt(request)
    }
}
