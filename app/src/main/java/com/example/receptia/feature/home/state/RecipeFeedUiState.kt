package com.example.receptia.feature.home.state

import com.example.receptia.persistence.Recipe

sealed interface RecipeFeedUiState {
    object Loading : RecipeFeedUiState

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeFeedUiState
}
