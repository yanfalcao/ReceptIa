package com.example.receptia.feature.historic.preview

import com.example.receptia.persistence.Recipe

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
        return Recipe().apply {
            this.name = name
            prepTime = "30 min"
            difficult = "Fácil"
            isFavorite = true
            amountCalories = "450 kcal"
            amountCarbs = "60g"
            amountProteins = "15g"
            amountPeopleServes = 2
        }
    }
}
