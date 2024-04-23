package com.nexusfalcao.createrecipe.state

sealed interface CheckFieldUiState {
    companion object {
        fun isUnfilled(
            checkFieldUiState: CheckFieldUiState,
            recipeField: RecipeFieldState
        ): Boolean {
            return checkFieldUiState is Unfilled && checkFieldUiState.fields.contains(recipeField)
        }
    }
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
