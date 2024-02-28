package com.nexusfalcao.receptia.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.receptia.feature.home.preview.PreviewParameterData.recipeList
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.Step
import java.util.Date
import java.util.UUID

class RecipesPreviewParameterProvider : PreviewParameterProvider<List<Recipe>> {
    override val values: Sequence<List<Recipe>>
        get() = sequenceOf(recipeList)
}

object PreviewParameterData {
    val recipeList = listOf(
        recipe.apply { isFavorite = false },
        recipe.apply { isFavorite = false },
        recipe,
        recipe,
        recipe,
        recipe,
    )

    val recipe: Recipe
        get() = Recipe(
            id = "ba6b7165-5350-4009-ad30-9a118f55feef",
            name = "Espaguete com Molho de Cogumelos e Bacon",
            description = "Um delicioso espaguete com molho de cogumelhos e bacon",
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
            ingredients = listOf(
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Macarrão",
                    measure = "500g"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Cogumelo",
                    measure = "500g"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Bacon",
                    measure = "300g"
                )
            ),
            recipeSteps = listOf(
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 1,
                    description = "1. Passo um"
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 2,
                    description = "2. Passo dois"
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 3,
                    description = "3. Passo três"
                ),
            ),
            createdAt = Date()
        )
}
