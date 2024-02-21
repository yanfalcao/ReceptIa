package com.nexusfalcao.model

data class RecipePreference(
    var responseLanguage: String,
    var meal: String,
    val favoriteIngredients: List<String>,
    val nonFavoriteIngredients: List<String>,
    val allergicIngredients: List<String>,
    val intolerantIngredients: List<String>,
)
