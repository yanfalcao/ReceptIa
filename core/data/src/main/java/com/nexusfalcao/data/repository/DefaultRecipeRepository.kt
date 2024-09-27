package com.nexusfalcao.data.repository

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nexusfalcao.data.extensions.asIngredientEntity
import com.nexusfalcao.data.extensions.asRecipeEntity
import com.nexusfalcao.data.extensions.asStepEntity
import com.nexusfalcao.data.extensions.getRecipes
import com.nexusfalcao.data.extensions.prompt
import com.nexusfalcao.database.dao.IngredientDao
import com.nexusfalcao.database.dao.RecipeDao
import com.nexusfalcao.database.dao.StepDao
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipePreference
import com.nexusfalcao.network.model.request.GptFunction
import com.nexusfalcao.network.model.request.GptRequest
import com.nexusfalcao.network.model.request.GptTool
import com.nexusfalcao.network.model.request.GtpMessage
import com.nexusfalcao.network.retrofit.ChatgptNetworkApi
import com.nexusfalcao.network.util.RecipeRequestUtil

internal class DefaultRecipeRepository(
    private val recipeDao: RecipeDao?,
    private val ingredientDao: IngredientDao?,
    private val stepDao: StepDao?,
    private val chatgptNetworkApi: ChatgptNetworkApi,
) : RecipeRepository {
    val crashlytics = FirebaseCrashlytics.getInstance()

    override fun insertRecipe(recipe: Recipe): Boolean {
        val rowsAffected = recipeDao?.insert(recipe.asRecipeEntity())
        recipe.ingredients.forEach { ingredient ->
            ingredientDao?.insert(ingredient.asIngredientEntity(recipe.id))
        }
        recipe.recipeSteps.forEach { step ->
            stepDao?.insert(step.asStepEntity(recipe.id))
        }
        return rowsAffected != null && rowsAffected > 0
    }

    override fun findRecipe(recipeId: String): Recipe? {
        return recipeDao?.findById(recipeId)?.asRecipeEntity()
    }

    override fun findRecipes(limit: Int): List<Recipe> {
        return recipeDao?.findLimited(limit)?.map { item ->
            item.asRecipeEntity()
        } ?: listOf()
    }

    override fun findRecipes(): List<Recipe> {
        return recipeDao?.findAll()?.map { item ->
            item.asRecipeEntity()
        } ?: listOf()
    }

    override fun updateIsFavorite(
        recipeId: String,
        isFavorite: Boolean,
    ): Boolean {
        val rowsAffected = recipeDao?.updateIsFavorite(recipeId, isFavorite)

        return rowsAffected != null && rowsAffected > 0
    }

    override suspend fun callNewRecipe(
        preference: RecipePreference,
        apiModel: String,
    ): List<Recipe> {
        return try {
            val request = getNewRecipeRequest(preference, apiModel)
            val response = chatgptNetworkApi.createNewRecipe(request)

            response.body()?.getRecipes() ?: listOf()
        } catch (e: Exception) {
            e.printStackTrace()
            crashlytics.recordException(e)
            listOf()
        }
    }

    private fun getNewRecipeRequest(
        preference: RecipePreference,
        apiModel: String,
    ): GptRequest {
        return GptRequest(
            model = apiModel,
            messages =
                listOf(
                    GtpMessage(
                        role = "system",
                        content = RecipeRequestUtil.systemContent,
                    ),
                    GtpMessage(
                        role = "user",
                        content = preference.prompt(),
                    ),
                ),
            tools =
                listOf(
                    GptTool(
                        type = "function",
                        function =
                            GptFunction(
                                name = "get_recipe",
                                parameters = RecipeRequestUtil.schema,
                            ),
                    ),
                ),
            toolChoice = "auto",
        )
    }
}
