package com.example.receptia.feature.newRecipe.state

sealed interface CheckFieldUiState {
    object None : CheckFieldUiState
    object Filled : CheckFieldUiState

    data class Unfilled(
        val field: RecipeFieldState,
    ) : CheckFieldUiState
}
