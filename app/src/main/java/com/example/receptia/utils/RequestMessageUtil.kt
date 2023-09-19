package com.example.receptia.utils

import com.example.receptia.model.RecipePreferences
import com.google.gson.Gson

object RequestMessageUtil {
    fun newRecipePrompt(preferences: RecipePreferences): String {
        val preferencesJson = Gson().toJson(preferences)
        return "Pretend you are a Backend API that generates recipes from tastes provided through " +
            "ingredients.  Just answer a Json like a API.\n" +
            "All the recipes need to be classified in 3 levels: easy, medium and hard. The field " +
            "difficult_level in the response format below will be an integer.\n\n" +
            "Format the response information as:\n" +
            "{\"recipe_name\":\"recipe_name\",\"description\":\"description\"," +
            "\"preparation_method\":[{\"step\":\"step\"}],\"amount_calories\":\"amount_calories\"," +
            "\"amount_carbo\":\"amount_carbo\",\"amount_proteins\":\"amount_proteins\"," +
            "\"preparation_time\":\"preparation_time\",\"difficulty_level\":1," +
            "\"recipe_difficulty\":\"recipe_difficulty\"," + "\"amount_people_serves\":1," +
            "\"ingredients\":[{\"name\":\"name\",\"measure\":\"measure\"}]}\n\n" +
            "Request: $preferencesJson"
    }
}
