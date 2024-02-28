package com.nexusfalcao.receptia.utils

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.Step
import java.lang.reflect.Type
import java.util.Date
import java.util.UUID

class RecipeDeserializer : JsonDeserializer<Recipe> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Recipe {
        val recipeJsonObject = extractRecipeJsonObject(json) ?: throw JsonParseException("Json parse exception")
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
                steps.add(Step(
                    id = UUID.randomUUID().toString(),
                    position = index + 1,
                    description = stepsArray.getJsonObject(index).getString("step")
                ))
            }

            val ingredients: MutableList<Ingredient> = mutableListOf()
            val ingredientsArray = getAsJsonArray("ingredients")
            for (ingredientElement in ingredientsArray) {
                val ingredient = Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = ingredientElement.asJsonObject.getString("name"),
                    measure = ingredientElement.asJsonObject.getString("measure")
                )

                ingredients.add(ingredient)
            }

            recipe = Recipe(
                id = UUID.randomUUID().toString(),
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

    private fun extractRecipeJsonObject(json: JsonElement?): JsonObject? {
        return json?.let {
            val recipeString = it.asJsonObject
                .getAsJsonArray("choices")
                .getJsonObject(0)
                .getAsJsonObject("message")
                .getAsJsonArray("tool_calls")
                .getJsonObject(0)
                .getAsJsonObject("function")
                .getString("arguments")
            Gson().fromJson(recipeString, JsonObject::class.java)
        }
    }
}
