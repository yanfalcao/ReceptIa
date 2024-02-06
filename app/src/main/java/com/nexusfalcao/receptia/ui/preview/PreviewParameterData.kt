package com.nexusfalcao.receptia.ui.preview

import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.persistence.Ingredient
import com.nexusfalcao.receptia.persistence.Recipe
import com.nexusfalcao.receptia.persistence.User

object PreviewParameterData {
    val recipe: Recipe
        get() = Recipe().apply {
            name = "Espaguete com Molho de Cogumelos e Bacon"
            description = "Filé de frango suculento temperado com limão e acompanhado de  cogumelos salteados."
            prepTime = "30 min"
            difficult = "Fácil"
            isFavorite = true
            amountCalories = "450 kcal"
            amountCarbs = "60g"
            amountProteins = "15g"
            amountPeopleServes = 2
            ingredients.add(
                Ingredient().apply {
                    name = "Filé de Frango"
                    measure = "2 unid"
                },
            )
            ingredients.add(
                Ingredient().apply {
                    name = "Limão"
                    measure = "1 unid"
                },
            )
            ingredients.add(
                Ingredient().apply {
                    name = "Cogumelos"
                    measure = "200g"
                },
            )
            ingredients.add(
                Ingredient().apply {
                    name = "Alho"
                    measure = "3 dentes"
                },
            )
            ingredients.add(
                Ingredient().apply {
                    name = "Bacon"
                    measure = "50g"
                },
            )
            recipeSteps = "1. Tempere os filés de frango com suco de limão, alho picado, sal e pimenta a gosto." +
                "\n\n2. Em uma frigideira, frite o bacon até ficar crocante. Retire o bacon da frigideira e reserve." +
                "\n\n3. Na mesma frigideira, adicione os filés de frango temperados e cozinhe em fogo médio até ficarem dourados e cozidos por completo." +
                "\n\n4. Retire os filés de frango da frigideira e reserve." +
                "\n\n5. Na mesma frigideira, adicione os cogumelos fatiados e cozinhe por alguns minutos até ficarem macios." +
                "\n\n6. Adicione o tomate picado, o manjericão e a cebolinha à frigideira e cozinhe por mais alguns minutos." +
                "\n\n7. Retorne os filés de frango à frigideira e misture tudo delicadamente." +
                "\n\n8. Sirva o filé de frango ao limão com cogumelos acompanhado do bacon crocante por cima."
        }

    val user: User
        get() = User().apply {
            name = "Yan Falcão"
            photoId = R.drawable.img_man_2
        }
}
