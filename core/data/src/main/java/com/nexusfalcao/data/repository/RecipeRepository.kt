package com.nexusfalcao.data.repository

import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipePreference

interface RecipeRepository {
    fun insertRecipe(recipe: Recipe): Boolean

    fun findRecipe(recipeId: String): Recipe?

    fun findRecipes(limit: Int): List<Recipe>

    fun findRecipes(): List<Recipe>

    fun removeRecipe(recipe: Recipe): Boolean

    fun updateIsFavorite(recipeId: String, isFavorite: Boolean): Boolean

    suspend fun callNewRecipe(preference: RecipePreference, apiModel: String): List<Recipe>
}