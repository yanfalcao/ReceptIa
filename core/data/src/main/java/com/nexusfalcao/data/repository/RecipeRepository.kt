package com.nexusfalcao.data.repository

import com.nexusfalcao.model.Recipe

interface RecipeRepository {
    fun insertRecipe(recipe: Recipe): Boolean

    fun findRecipe(recipeId: String): Recipe?

    fun findRecipes(limit: Int): List<Recipe>

    fun findRecipes(): List<Recipe>

    fun updateIsFavorite(recipeId: String, isFavorite: Boolean): Boolean
}