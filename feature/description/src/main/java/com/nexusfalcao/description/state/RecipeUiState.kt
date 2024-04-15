package com.nexusfalcao.description.state

import com.nexusfalcao.model.Recipe

sealed interface RecipeUiState {
    object Loading : RecipeUiState

    data class Success(
        val recipe: Recipe,
    ) : RecipeUiState
}
