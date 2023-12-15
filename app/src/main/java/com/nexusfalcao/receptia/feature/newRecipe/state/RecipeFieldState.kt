package com.nexusfalcao.receptia.feature.newRecipe.state

sealed interface RecipeFieldState {
    object MEAL : RecipeFieldState
    object FAVORITE : RecipeFieldState
    object NON_FAVORITE : RecipeFieldState
    object ALLERGIC : RecipeFieldState
    object INTOLERANT : RecipeFieldState
}
