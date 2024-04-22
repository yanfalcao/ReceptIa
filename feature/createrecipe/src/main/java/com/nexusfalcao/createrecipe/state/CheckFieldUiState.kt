package com.nexusfalcao.createrecipe.state

sealed interface CheckFieldUiState {
    object None : CheckFieldUiState
    object Filled : CheckFieldUiState

    data class Unfilled(
        val fields: MutableList<RecipeFieldState>,
    ) : CheckFieldUiState {
        fun equalsField(recipeField: RecipeFieldState): Boolean {
            return fields.contains(recipeField)
        }
    }
}
