package com.nexusfalcao.model

import com.nexusfalcao.model.state.RecipeDifficult

data class RecipeDetails(
    val amountCalories: String,
    val amountCarbs: String,
    val amountProteins: String,
    val preparationTime: String,
    val difficult: String,
    val difficultLevel: Int,
    val amountPeopleServes: Int,
) {
    var recipeDifficult: RecipeDifficult = RecipeDifficult.Easy
        private set
        get() {
            return when(difficultLevel) {
                1 -> RecipeDifficult.Easy
                2 -> RecipeDifficult.Medium
                3 -> RecipeDifficult.Hard
                else -> RecipeDifficult.Easy
            }
        }
}
