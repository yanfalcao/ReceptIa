package com.example.receptia.model

import com.google.gson.annotations.SerializedName

data class RecipePreferences(
    @SerializedName("response_anwser_language")
    var responseLanguage: String = "Português",
    @SerializedName("meal")
    var meal: String = "",
    @SerializedName("favorite_ingredients")
    val favoriteIngredients: MutableList<String> = mutableListOf(),
    @SerializedName("non_favorite_ingredients")
    val nonFavoriteIngredients: MutableList<String> = mutableListOf(),
    @SerializedName("allergic_ingredients")
    val allergicIngredients: MutableList<String> = mutableListOf(),
    @SerializedName("intolerant_ingredients")
    val intolerantIngredients: MutableList<String> = mutableListOf(),
) {
    private fun toStringList(list: MutableList<String>): String {
        var text = ""
        list.forEachIndexed { index, item ->
            if(index == (list.size-1)) {
                text += item
            } else {
                text += "${item},"
            }
        }

        return text
    }
    override fun toString(): String {
        var text = "\n- All the recipe data need to be in ${responseLanguage};"
        text += "\n- ${meal};"
        text += "\n- Favorite ingredients: ${toStringList(favoriteIngredients)};"
        if(nonFavoriteIngredients.isNotEmpty()) {
            text += "\n- Non Favorite ingredients: ${toStringList(nonFavoriteIngredients)};"
        }
        if(allergicIngredients.isNotEmpty()) {
            text += "\n- Allergic ingredients: ${toStringList(allergicIngredients)};"
        }
        if(intolerantIngredients.isNotEmpty()) {
            text += "\n- Intolerant ingredients: ${toStringList(intolerantIngredients)};"
        }

        return text
    }
}
