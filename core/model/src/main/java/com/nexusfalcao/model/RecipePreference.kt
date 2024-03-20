package com.nexusfalcao.model

data class RecipePreference(
    var responseLanguage: String,
    var meal: String,
    var favoriteIngredients: List<String>,
    var nonFavoriteIngredients: List<String>,
    var allergicIngredients: List<String>,
    var intolerantIngredients: List<String>,
)
