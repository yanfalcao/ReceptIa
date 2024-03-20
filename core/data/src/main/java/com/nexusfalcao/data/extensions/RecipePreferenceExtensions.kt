package com.nexusfalcao.data.extensions

import com.nexusfalcao.model.RecipePreference

internal fun RecipePreference.prompt(): String {
    var text = """Return a recipe based in this preferences:
        |* Recipe data language: ${responseLanguage};
        |* Meal: ${meal};
        |* Favorite ingredients: ${promptIngredients(favoriteIngredients)};
    """.trimMargin()

    if(nonFavoriteIngredients.isNotEmpty()) {
        text += "\n* Non Favorite ingredients: ${promptIngredients(nonFavoriteIngredients)};"
    }
    if(allergicIngredients.isNotEmpty()) {
        text += "\n* Allergic ingredients: ${promptIngredients(allergicIngredients)};"
    }
    if(intolerantIngredients.isNotEmpty()) {
        text += "\n* Intolerant ingredients: ${promptIngredients(intolerantIngredients)};"
    }

    return text
}
private fun promptIngredients(ingredients: List<String>): String {
    var text = ""
    ingredients.forEachIndexed { index, item ->
        if(index == (ingredients.size-1)) {
            text += item
        } else {
            text += "${item},"
        }
    }

    return text
}
