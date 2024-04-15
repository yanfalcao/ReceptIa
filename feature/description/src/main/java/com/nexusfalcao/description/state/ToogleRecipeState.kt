package com.nexusfalcao.description.state

sealed interface ToogleRecipeState {
    object DetailsSelected : ToogleRecipeState

    object RecipeSelected : ToogleRecipeState
}
