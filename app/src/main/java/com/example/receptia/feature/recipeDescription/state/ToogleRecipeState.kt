package com.example.receptia.feature.recipeDescription.state

sealed interface ToogleRecipeState {
    object DetailsSelected : ToogleRecipeState

    object RecipeSelected : ToogleRecipeState
}
