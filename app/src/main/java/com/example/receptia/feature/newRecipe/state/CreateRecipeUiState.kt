package com.example.receptia.feature.newRecipe.state

sealed interface CreateRecipeUiState {
    object None : CreateRecipeUiState
    object Loading : CreateRecipeUiState
    data class Error(val message: String) : CreateRecipeUiState
    data class Success(val recipeId: String) : CreateRecipeUiState
}
