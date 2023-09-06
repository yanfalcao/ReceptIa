package com.example.receptia.model

import com.google.gson.annotations.SerializedName

data class RecipePreferences(
    @SerializedName("response_anwser_language")
    var responseLanguage: String = "Português",
    @SerializedName("meal")
    var meal: String? = null,
    @SerializedName("favorite_ingredients")
    val favoriteIngredients: MutableList<String> = mutableListOf(),
    @SerializedName("non_favorite_ingredients")
    val nonFavoriteIngredients: MutableList<String> = mutableListOf(),
    @SerializedName("allergic_ingredients")
    val allergicIngredients: MutableList<String> = mutableListOf(),
    @SerializedName("intolerant_ingredients")
    val intolerantIngredients: MutableList<String> = mutableListOf(),
)
