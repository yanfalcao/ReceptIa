package com.nexusfalcao.createrecipe.state

sealed interface CreateRecipeUiState {
    object None : CreateRecipeUiState
    object Loading : CreateRecipeUiState
    object Error : CreateRecipeUiState
    data class Success(val recipeId: String) : CreateRecipeUiState
}
