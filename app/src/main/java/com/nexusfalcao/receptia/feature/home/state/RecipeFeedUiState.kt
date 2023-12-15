package com.nexusfalcao.receptia.feature.home.state

import com.nexusfalcao.receptia.persistence.Recipe

sealed interface RecipeFeedUiState {
    object Loading : RecipeFeedUiState

    data class Success(
        val recipes: List<Recipe>,
    ) : RecipeFeedUiState
}
