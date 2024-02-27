package com.nexusfalcao.model

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val recipeSteps: List<Step>,
    val isFavorite: Boolean,
    val recipeDetails: RecipeDetails,
)
