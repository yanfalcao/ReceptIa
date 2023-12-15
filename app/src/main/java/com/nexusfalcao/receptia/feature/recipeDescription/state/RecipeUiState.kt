package com.nexusfalcao.receptia.feature.recipeDescription.state

import com.nexusfalcao.receptia.persistence.Recipe

sealed interface RecipeUiState {
    object Loading : RecipeUiState

    data class Success(
        val recipe: Recipe,
    ) : RecipeUiState
}
