package com.nexusfalcao.receptia.feature.recipeDescription.state

sealed interface ToogleRecipeState {
    object DetailsSelected : ToogleRecipeState

    object RecipeSelected : ToogleRecipeState
}
