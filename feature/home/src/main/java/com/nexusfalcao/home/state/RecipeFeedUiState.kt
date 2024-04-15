package com.nexusfalcao.home.state

import com.nexusfalcao.model.Recipe

sealed interface RecipeFeedUiState {
    object Loading : RecipeFeedUiState

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeFeedUiState
}
