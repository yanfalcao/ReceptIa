package com.nexusfalcao.data.extensions

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.Step
import com.nexusfalcao.network.model.response.GptResponse
import java.util.Date

internal fun GptResponse.getRecipes(): List<Recipe> {
    val recipeArguments = mutableListOf<String>()

    choices.forEach { choice ->
        choice.messageResponse.toolCalls.forEach{ toolCall ->
            val argument = toolCall.function.arguments
            recipeArguments.add(argument)
        }
    }

    return recipeArguments.map { argument ->
        deserializeRecipe(argument)
    }
}

private fun deserializeRecipe(jsonString: String): Recipe {
    val recipeJsonObject = Gson().fromJson(jsonString, JsonObject::class.java)
        ?: throw JsonParseException("Json parse exception")
    val recipe: Recipe

    with(recipeJsonObject) {
        val recipeDetails = RecipeDetails(
            amountCalories = getString("amount_calories"),
            amountCarbs = getString("amount_carbo"),
            amountProteins = getString("amount_proteins"),
            preparationTime = getString("preparation_time"),
            difficult = getString("recipe_difficulty"),
            difficultLevel = getInt("difficulty_level"),
            amountPeopleServes = getInt("amount_people_serves")
        )

        val steps: MutableList<Step> = mutableListOf()
        val stepsArray = getAsJsonArray("preparation_method")
        for (index in stepsArray.asList().indices) {
            steps.add(
                Step(
                    id = java.util.UUID.randomUUID().toString(),
                    position = index + 1,
                    description = stepsArray.getJsonObject(index).getString("step")
                )
            )
        }

        val ingredients: MutableList<Ingredient> = mutableListOf()
        val ingredientsArray = getAsJsonArray("ingredients")
        for (ingredientElement in ingredientsArray) {
            val ingredient = Ingredient(
                id = java.util.UUID.randomUUID().toString(),
                name = ingredientElement.asJsonObject.getString("name"),
                measure = ingredientElement.asJsonObject.getString("measure")
            )

            ingredients.add(ingredient)
        }

        recipe = Recipe(
            id = java.util.UUID.randomUUID().toString(),
            name = getString("recipe_name"),
            description = getString("recipe_description"),
            recipeDetails = recipeDetails,
            isFavorite = false,
            recipeSteps = steps,
            ingredients = ingredients,
            createdAt = Date()
        )
    }

    return recipe
}
