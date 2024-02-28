package com.nexusfalcao.data.repository

import com.nexusfalcao.model.Recipe

interface RecipeRepository {
    fun saveRecipe(recipe: Recipe): Boolean

    fun getRecipe(recipeId: String): Recipe?

    fun getRecipes(limit: Int): List<Recipe>

    fun getRecipes(): List<Recipe>

    fun updateIsFavorite(recipeId: String, isFavorite: Boolean): Boolean
}