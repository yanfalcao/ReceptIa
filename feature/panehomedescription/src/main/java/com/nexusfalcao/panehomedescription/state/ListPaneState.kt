package com.nexusfalcao.panehomedescription.state

import com.nexusfalcao.home.state.RecipeFeedUiState

data class ListPaneState(
    val feedState: RecipeFeedUiState,
    val appStoreUrl: String,
    val navigateToNewRecipe: () -> Unit = {},
    val isRequireUpdate: Boolean,
)
