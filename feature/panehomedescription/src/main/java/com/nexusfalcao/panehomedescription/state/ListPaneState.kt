package com.nexusfalcao.panehomedescription.state

import com.nexusfalcao.home.state.RecipeFeedUiState
import com.nexusfalcao.model.Recipe

data class ListPaneState(
    val feedState: RecipeFeedUiState,
    val appStoreUrl: String,
    val navigateToNewRecipe: () -> Unit = {},
    val removeRecipe: (Recipe) -> Unit = {},
    val isRequireUpdate: Boolean,
)
