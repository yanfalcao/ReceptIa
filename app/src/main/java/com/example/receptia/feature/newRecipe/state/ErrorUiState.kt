package com.example.receptia.feature.newRecipe.state

sealed interface ErrorUiState {
    object None : ErrorUiState
    object IngredientMaxLimit : ErrorUiState {
        override fun equals(other: Any?): Boolean {
            return false
        }
    }
}
