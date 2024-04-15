package com.nexusfalcao.recipecatalog.state

import com.nexusfalcao.model.Recipe

sealed interface CatalogUiState {
    object Loading : CatalogUiState

    data class Success(
        val recipes: List<Recipe>,
    ) : CatalogUiState
}
