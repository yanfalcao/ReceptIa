package com.nexusfalcao.receptia.feature.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.nexusfalcao.receptia.feature.home.preview.PreviewParameterData.recipeList
import com.nexusfalcao.receptia.persistence.Ingredient
import com.nexusfalcao.receptia.persistence.Recipe
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

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
        get() = Recipe().apply {
            id = "ba6b7165-5350-4009-ad30-9a118f55feef"
            name = "Espaguete com Molho de Cogumelos e Bacon"
            description = "Um delicioso espaguete com molho de cogumelhos e bacon"
            prepTime = "30 min"
            difficult = "Fácil"
            isFavorite = true
            amountCalories = "450 kcal"
            amountCarbs = "60g"
            amountProteins = "15g"
            amountPeopleServes = 2
            ingredients = realmListOf(
                Ingredient().apply {
                    name = "Macarrão"
                    measure = "500g"
                },
                Ingredient().apply {
                    name = "Cogumelo"
                    measure = "500g"
                },
                Ingredient().apply {
                    name = "Bacon"
                    measure = "300g"
                }
            )
            recipeSteps = "1. Passo um\n\n2. Passo dois\n\n3. Passo três"
        }
}
