package com.example.receptia.model

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val recipeSteps: String,
    val amountCalories: String,
    val amountCarbs: String,
    val amountProteins: String,
    val prepTime: String,
    val easeRecipe: String,
    val amountPeopleServes: Int,
    val isFavorite: Boolean
)
