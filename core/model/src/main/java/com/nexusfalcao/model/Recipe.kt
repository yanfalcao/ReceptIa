package com.nexusfalcao.model

import java.util.Date

data class Recipe(
    val id: String,
    val name: String,
    val description: String,
    val ingredients: List<Ingredient>,
    val recipeSteps: List<Step>,
    var isFavorite: Boolean,
    val recipeDetails: RecipeDetails,
    val createdAt: Date
) {
    fun toogleIsFavorite() {
        isFavorite = !isFavorite
    }

    fun stepsToString(): String {
        var text: String = ""
        recipeSteps.forEach{ step ->
            text += step.toString() + "\n"
        }

        return text
    }
}
