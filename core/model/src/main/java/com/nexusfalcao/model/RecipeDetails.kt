package com.nexusfalcao.model

data class RecipeDetails(
    val amountCalories: String,
    val amountCarbs: String,
    val amountProteins: String,
    val preparationTime: String,
    val difficult: String,
    val difficultLevel: Int,
    val amountPeopleServes: Int,
)
