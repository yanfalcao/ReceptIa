package com.nexusfalcao.receptia.persistence.utils

import com.google.gson.Gson
import com.nexusfalcao.receptia.persistence.Ingredient
import com.nexusfalcao.receptia.persistence.Recipe
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.nexusfalcao.receptia.persistence.extension.*
import java.lang.reflect.Type

class RecipeDeserializer : JsonDeserializer<Recipe> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Recipe {
        val recipeJsonObject = extractRecipeJsonObject(json) ?: throw JsonParseException("Json parse exception")

        return Recipe().apply {
            with(recipeJsonObject) {
                name = getString("recipe_name")
                description = getString("recipe_description")
                amountCalories = getString("amount_calories")
                amountCarbs = getString("amount_carbo")
                amountProteins = getString("amount_proteins")
                prepTime = getString("preparation_time")
                difficult = getString("recipe_difficulty")
                amountPeopleServes = getInt("amount_people_serves")
                difficultLevel = getInt("difficulty_level")

                val stepsArray = getAsJsonArray("preparation_method")
                for (index in stepsArray.asList().indices) {
                    val step = stepsArray.getJsonObject(index).getString("step")
                    val stepNumber = index + 1
                    recipeSteps += "${stepNumber}. ${step}\n\n"
                }

                val ingredientsArray = getAsJsonArray("ingredients")
                for (ingredientElement in ingredientsArray) {
                    val ingredient = Ingredient()
                    ingredient.name = ingredientElement.asJsonObject.getString("name")
                    ingredient.measure = ingredientElement.asJsonObject.getString("measure")

                    ingredients.add(ingredient)
                }
            }
        }
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
