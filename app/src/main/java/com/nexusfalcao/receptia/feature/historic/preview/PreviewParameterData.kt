package com.nexusfalcao.receptia.feature.historic.preview

import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import java.util.Date
import java.util.UUID

object PreviewParameterData {
    val recipeList: List<Recipe>
        get() = listOf(
            RecipeMock(),
            RecipeMock("Filé de Frango ao Limão com Cogumelos"),
            RecipeMock("Waffle de Pão de Queijo"),
            RecipeMock("Muqueca de Peixe"),
            RecipeMock("Pão de Queijo"),
            RecipeMock(),
            RecipeMock(),
            RecipeMock("Filé de Frango ao Limão com Cogumelos"),
            RecipeMock("Waffle de Pão de Queijo"),
            RecipeMock("Muqueca de Peixe"),
            RecipeMock("Pão de Queijo"),
            RecipeMock(),
        )

    private fun RecipeMock(name: String = "Espaguete com Molho de Cogumelos e Bacon"): Recipe {
        return Recipe(
            id = UUID.randomUUID().toString(),
            name = name,
            isFavorite = true,
            recipeDetails = RecipeDetails(
                preparationTime = "30 min",
                difficult = "Fácil",
                amountCalories = "450 kcal",
                amountCarbs = "60g",
                amountProteins = "15g",
                amountPeopleServes = 2,
                difficultLevel = 1
            ),
            ingredients = listOf(),
            description = "",
            createdAt = Date(),
            recipeSteps = listOf()
        )
    }
}
