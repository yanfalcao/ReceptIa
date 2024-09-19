package com.nexusfalcao.createrecipe.state

sealed interface CheckFieldUiState {
    companion object {
        fun isEssencialFieldUnfilled(
            checkFieldUiState: CheckFieldUiState,
            recipeField: RecipeFieldState,
        ): Boolean {
            return (
                recipeField is RecipeFieldState.MEAL ||
                    recipeField is RecipeFieldState.FAVORITE
            ) &&
                checkFieldUiState is Unfilled &&
                checkFieldUiState.fields.contains(recipeField)
        }

        fun isUnfilled(
            checkFieldUiState: CheckFieldUiState,
            recipeField: RecipeFieldState,
        ): Boolean {
            return checkFieldUiState is Unfilled && checkFieldUiState.fields.contains(recipeField)
        }
    }

    object None : CheckFieldUiState

    object Filled : CheckFieldUiState

    data class Unfilled(
        val fields: MutableList<RecipeFieldState>,
    ) : CheckFieldUiState
}
