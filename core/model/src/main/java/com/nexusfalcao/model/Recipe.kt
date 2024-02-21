package com.nexusfalcao.model

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val recipeSteps: List<Step>,
    val amountCalories: String,
    val amountCarbs: String,
    val amountProteins: String,
    val preparationTime: String,
    val difficult: String,
    val difficultLevel: Int,
    val amountPeopleServes: Int,
    val isFavorite: Boolean,
)
