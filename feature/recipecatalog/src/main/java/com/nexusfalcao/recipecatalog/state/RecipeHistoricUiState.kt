package com.nexusfalcao.recipecatalog.state

import com.nexusfalcao.model.Recipe

sealed interface RecipeHistoricUiState {
    object Loading : RecipeHistoricUiState

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeHistoricUiState
}
