package com.nexusfalcao.receptia.feature.historic.state

import com.nexusfalcao.receptia.persistence.Recipe

sealed interface RecipeHistoricUiState {
    object Loading : RecipeHistoricUiState

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeHistoricUiState
}
