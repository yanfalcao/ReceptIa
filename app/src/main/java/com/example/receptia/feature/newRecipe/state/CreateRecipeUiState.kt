package com.example.receptia.feature.newRecipe.state

sealed interface CreateRecipeUiState {
    object None : CreateRecipeUiState
    object Loading : CreateRecipeUiState
    object Error : CreateRecipeUiState
    object Success : CreateRecipeUiState
}
