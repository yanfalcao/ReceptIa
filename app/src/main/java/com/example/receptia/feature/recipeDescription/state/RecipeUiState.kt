package com.example.receptia.feature.recipeDescription.state

import com.example.receptia.persistence.Recipe

sealed interface RecipeUiState {
    object Loading : RecipeUiState

    data class Success(
        val recipe: Recipe,
    ) : RecipeUiState
}
