package com.nexusfalcao.createrecipe.state

data class IngredientUiState(
    val ingredients: List<String> = listOf(),
    val state: RecipeFieldState,
)
