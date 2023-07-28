package com.example.receptia.feature.historic.state

import com.example.receptia.model.Recipe

sealed interface RecipeHistoricUiState {
    object Loading : RecipeHistoricUiState

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeHistoricUiState
}
