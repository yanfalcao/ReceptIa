package com.example.receptia.model

data class RecipePreferences(
    var meal: String? = null,
    val favoriteIngredients: MutableList<String> = mutableListOf(),
    val nonFavoriteIngredients: MutableList<String> = mutableListOf(),
    val allergicIngredients: MutableList<String> = mutableListOf(),
    val intolerantIngredients: MutableList<String> = mutableListOf(),
)
