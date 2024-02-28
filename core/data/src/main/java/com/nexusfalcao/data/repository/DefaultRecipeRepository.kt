package com.nexusfalcao.data.repository

import android.app.Application
import com.nexusfalcao.data.extensions.asIngredientEntity
import com.nexusfalcao.data.extensions.asRecipeEntity
import com.nexusfalcao.data.extensions.asStepEntity
import com.nexusfalcao.database.ReceptIaDatabase
import com.nexusfalcao.database.dao.IngredientDao
import com.nexusfalcao.database.dao.RecipeDao
import com.nexusfalcao.database.dao.StepDao
import com.nexusfalcao.model.Recipe

internal class DefaultRecipeRepository(
    private val appContext: Application
) : RecipeRepository {
    private val recipeDao: RecipeDao?
        get() = ReceptIaDatabase.getInstance(appContext)?.recipeDao()
    private val ingredientDao: IngredientDao?
        get() = ReceptIaDatabase.getInstance(appContext)?.ingredientDao()
    private val stepDao: StepDao?
        get() = ReceptIaDatabase.getInstance(appContext)?.stepDao()

    override fun saveRecipe(recipe: Recipe): Boolean {
        val rowsAffected = recipeDao?.insert(recipe.asRecipeEntity())
        recipe.ingredients.forEach { ingredient ->
            ingredientDao?.insert(ingredient.asIngredientEntity(recipe.id))
        }
        recipe.recipeSteps.forEach { step ->
            stepDao?.insert(step.asStepEntity(recipe.id))
        }
        return rowsAffected != null && rowsAffected > 0
    }

    override fun getRecipe(recipeId: String): Recipe? {
        return recipeDao?.findById(recipeId)?.asRecipeEntity()
    }

    override fun getRecipes(limit: Int): List<Recipe> {
        return recipeDao?.findLimited(limit)?.map { item ->
            item.asRecipeEntity()
        } ?: listOf()
    }

    override fun getRecipes(): List<Recipe> {
        return recipeDao?.findAll()?.map { item ->
            item.asRecipeEntity()
        } ?: listOf()
    }

    override fun updateIsFavorite(recipeId: String, isFavorite: Boolean): Boolean {
        val rowsAffected = recipeDao?.updateIsFavorite(recipeId, isFavorite)

        return rowsAffected != null && rowsAffected > 0
    }
}