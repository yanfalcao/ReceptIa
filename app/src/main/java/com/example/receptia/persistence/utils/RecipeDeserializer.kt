package com.example.receptia.persistence.utils

import com.example.receptia.persistence.Ingredient
import com.example.receptia.persistence.Recipe
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class RecipeDeserializer : JsonDeserializer<Recipe> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?,
    ): Recipe {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Json parse exception")
        val recipe = Recipe()

        recipe.name = jsonObject.get("recipe_name").asString
        recipe.description = jsonObject.get("recipe_description").asString
        recipe.amountCalories = jsonObject.get("amount_calories").asString
        recipe.amountCarbs = jsonObject.get("amount_carbo").asString
        recipe.amountProteins = jsonObject.get("amount_proteins").asString
        recipe.prepTime = jsonObject.get("preparation_time").asString
        recipe.difficult = jsonObject.get("recipe_difficulty").asString
        recipe.amountPeopleServes = jsonObject.get("amount_people_serves").asInt
        recipe.difficultLevel = jsonObject.get("difficulty_level").asInt

        val stepsArray = jsonObject.getAsJsonArray("preparation_method")
        for (index in stepsArray.asList().indices) {
            val step = stepsArray[index].asJsonObject.get("step").asString
            val stepNumber = index + 1
            recipe.recipeSteps += "${stepNumber}. ${step}\n\n"
        }

        val ingredientsArray = jsonObject.getAsJsonArray("ingredients")
        for (ingredientElement in ingredientsArray) {
            val ingredient = Ingredient()
            ingredient.name = ingredientElement.asJsonObject.get("name").asString
            ingredient.measure = ingredientElement.asJsonObject.get("measure").asString

            recipe.ingredients.add(ingredient)
        }

        return recipe
    }
}
