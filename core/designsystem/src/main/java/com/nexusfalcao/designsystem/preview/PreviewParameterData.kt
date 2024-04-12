package com.nexusfalcao.designsystem.preview

import com.nexusfalcao.designsystem.R
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.User
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.Step
import java.util.Date
import java.util.UUID

object PreviewParameterData {
    val recipe: Recipe
        get() = Recipe(
            id = UUID.randomUUID().toString(),
            name = "Espaguete com Molho de Cogumelos e Bacon",
            description = "Filé de frango suculento temperado com limão e acompanhado de  cogumelos salteados.",
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
                    name = "Filé de Frango",
                    measure = "2 unid"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Limão",
                    measure = "1 unid"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Cogumelos",
                    measure = "200g"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Alho",
                    measure = "3 dentes"
                ),
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Bacon",
                    measure = "50g"
                )
            ),
            recipeSteps = listOf(
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 1,
                    description = "Tempere os filés de frango com suco de limão, alho picado, sal e pimenta a gosto."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 2,
                    description = "Em uma frigideira, frite o bacon até ficar crocante. Retire o bacon da frigideira e reserve."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 3,
                    description = "Na mesma frigideira, adicione os filés de frango temperados e cozinhe em fogo médio até ficarem dourados e cozidos por completo."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 4,
                    description = "Retire os filés de frango da frigideira e reserve."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 5,
                    description = "Na mesma frigideira, adicione os cogumelos fatiados e cozinhe por alguns minutos até ficarem macios."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 6,
                    description = "Adicione o tomate picado, o manjericão e a cebolinha à frigideira e cozinhe por mais alguns minutos."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 7,
                    description = "Retorne os filés de frango à frigideira e misture tudo delicadamente."
                ),
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 8,
                    description = "Sirva o filé de frango ao limão com cogumelos acompanhado do bacon crocante por cima."
                )
            ),
            createdAt = Date()
        )

    val user: User
        get() = User(
            id = UUID.randomUUID().toString(),
            name = "Yan Falcão",
            photoId = R.drawable.img_man_2,
            isLoggedIn = true,
        )
}