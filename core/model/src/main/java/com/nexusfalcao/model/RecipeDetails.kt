package com.nexusfalcao.model

import com.nexusfalcao.model.state.DifficultState

data class RecipeDetails(
    val amountCalories: String,
    val amountCarbs: String,
    val amountProteins: String,
    val preparationTime: String,
    val difficult: String,
    val difficultLevel: Int,
    val amountPeopleServes: Int,
) {
    var difficultState: DifficultState = DifficultState.Easy
        private set
        get() {
            return when(difficultLevel) {
                1 -> DifficultState.Easy
                2 -> DifficultState.Medium
                3 -> DifficultState.Hard
                else -> DifficultState.Easy
            }
        }
}
