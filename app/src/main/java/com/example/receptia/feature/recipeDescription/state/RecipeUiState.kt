package com.example.receptia.feature.recipeDescription.state

import com.example.receptia.model.Recipe

sealed interface RecipeUiState {
    object Loading : RecipeUiState

    data class Success(
        val recipe: Recipe,
    ) : RecipeUiState
}
