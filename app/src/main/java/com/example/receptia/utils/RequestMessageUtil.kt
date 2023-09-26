package com.example.receptia.utils

import com.example.receptia.model.RecipePreferences
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject

object RequestMessageUtil {
    val schema: JsonObject
        get() {
            return JsonObject().apply {
                addProperty("type","object")
                add("properties", JsonObject().apply {
                    add("recipe_name", JsonObject().apply {
                        addProperty("type", "string")
                    })
                    add("recipe_description", JsonObject().apply {
                        addProperty("type", "string")
                    })
                    add("preparation_method", JsonObject().apply {
                        addProperty("type", "array")
                        add("items", JsonObject().apply {
                            addProperty("type", "object")
                            add("properties", JsonObject().apply {
                                add("step", JsonObject().apply {
                                    addProperty("type", "string")
                                })
                            })
                        })
                    })
                    add("amount_calories", JsonObject().apply {
                        addProperty("type", "string")
                    })
                    add("amount_carbo", JsonObject().apply {
                        addProperty("type", "string")
                    })
                    add("amount_proteins", JsonObject().apply {
                        addProperty("type", "string")
                    })
                    add("preparation_time", JsonObject().apply {
                        addProperty("type", "string")
                        addProperty("description", "The time and the unit of time as minutes or hour, e.g. 30 minutes")
                    })
                    add("difficulty_level", JsonObject().apply {
                        addProperty("type", "integer")
                        addProperty("description", "The dificulty level of recipe is classified in 3 levels: easy(1), medium(2) and hard(3)")
                    })
                    add("recipe_difficulty", JsonObject().apply {
                        addProperty("type", "string")
                        addProperty("description", "The recipe dificulty is classified in 3 levels: easy, medium and hard")
                    })
                    add("amount_people_serves", JsonObject().apply {
                        addProperty("type", "integer")
                    })
                    add("ingredients", JsonObject().apply {
                        addProperty("type", "array")
                        add("items", JsonObject().apply {
                            addProperty("type", "object")
                            add("properties", JsonObject().apply {
                                add("name", JsonObject().apply {
                                    addProperty("type", "string")
                                })
                                add("measure", JsonObject().apply {
                                    addProperty("type", "string")
                                })
                            })
                        })
                    })
                })
                add("required", JsonArray().apply {
                    add("recipe_name")
                    add("recipe_description")
                    add("preparation_method")
                    add("amount_calories")
                    add("amount_carbo")
                    add("amount_proteins")
                    add("preparation_time")
                    add("difficulty_level")
                    add("recipe_difficulty")
                    add("amount_people_serves")
                    add("ingredients")
                })
            }
        }

    val systemContent: String
        get() {
            return "Pretend you are a backend API that generates recipes from provided preferences data."
        }

    fun newRecipePrompt(preferences: RecipePreferences): String {
        return "Return a recipe based in this preferences: $preferences"
    }
}
