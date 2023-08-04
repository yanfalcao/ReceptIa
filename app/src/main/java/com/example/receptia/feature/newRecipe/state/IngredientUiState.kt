package com.example.receptia.feature.newRecipe.state

data class IngredientUiState(
    val ingredients: List<String> = listOf(),
    val state: RecipeFieldState,
)
