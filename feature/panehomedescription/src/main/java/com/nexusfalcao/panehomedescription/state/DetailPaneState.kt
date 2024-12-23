package com.nexusfalcao.panehomedescription.state

import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState

data class DetailPaneState(
    val toogleState: ToogleRecipeState,
    val recipeUiState: RecipeUiState,
    val onToogleFavorite: (String) -> Unit = {},
    val onSelectToogle: () -> Unit = {},
    val refreshPane: (String) -> Unit,
)
