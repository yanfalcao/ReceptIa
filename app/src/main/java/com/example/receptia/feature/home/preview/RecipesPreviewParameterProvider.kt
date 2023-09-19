package com.example.receptia.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.receptia.feature.home.preview.PreviewParameterData.recipeList
import com.example.receptia.persistence.Recipe

class RecipesPreviewParameterProvider : PreviewParameterProvider<List<Recipe>> {
    override val values: Sequence<List<Recipe>>
        get() = sequenceOf(recipeList)
}

object PreviewParameterData {
    val recipeList = listOf(
        recipe,
        recipe,
        recipe,
        recipe,
        recipe,
        recipe,
    )

    val recipe: Recipe
        get() = Recipe().apply {
            id = "ba6b7165-5350-4009-ad30-9a118f55feef"
            name = "Espaguete com Molho de Cogumelos e Bacon"
            description = ""
            prepTime = "30 min"
            difficult = "Fácil"
            isFavorite = true
            amountCalories = "450 kcal"
            amountCarbs = "60g"
            amountProteins = "15g"
            amountPeopleServes = 2
        }
}
