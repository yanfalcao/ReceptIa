package com.nexusfalcao.data.util

import com.nexusfalcao.database.model.IngredientEntity
import com.nexusfalcao.database.model.RecipeEntity
import com.nexusfalcao.database.model.StepEntity
import com.nexusfalcao.database.model.UserEntity
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.RecipePreference
import com.nexusfalcao.model.Step
import com.nexusfalcao.model.User
import java.util.Date

object ModelInstance {
    val userModel =
        User(
            id = "705b1e18-9fb9-482b-b8dc-ac50ed64495f",
            name = "Roberto da Costa",
            photoId = 1,
            isLoggedIn = true,
        )

    val userEntity =
        UserEntity(
            id = "705b1e18-9fb9-482b-b8dc-ac50ed64495f",
            name = "Roberto da Costa",
            photoId = 1,
            isLoggedIn = true,
        )

    val ingredientModel =
        Ingredient(
            id = "4672f128-de5c-418e-b4a5-a4d63b4d7e45",
            name = "Onion",
            measure = "1 unit",
        )

    val ingredientEntity =
        IngredientEntity(
            id = "4672f128-de5c-418e-b4a5-a4d63b4d7e45",
            name = "Onion",
            measure = "1 unit",
            recipeId = "abebfb13-f073-4b99-8c99-deeb9bfbb207",
        )

    val stepModel =
        Step(
            id = "06daf956-be28-428e-b36c-55f39f8a8b21",
            position = 1,
            description = "Cut in small pieces the onion",
        )

    val stepEntity =
        StepEntity(
            id = "06daf956-be28-428e-b36c-55f39f8a8b21",
            recipeId = "abebfb13-f073-4b99-8c99-deeb9bfbb207",
            position = 1,
            description = "Cut in small pieces the onion",
        )

    val recipeModel =
        Recipe(
            id = "abebfb13-f073-4b99-8c99-deeb9bfbb207",
            name = "Salad",
            description = "A delicious salad",
            ingredients = listOf(ingredientModel),
            recipeSteps = listOf(stepModel),
            isFavorite = true,
            recipeDetails =
                RecipeDetails(
                    amountCalories = "100 kcal",
                    amountCarbs = "20g",
                    amountProteins = "30g",
                    preparationTime = "40 min",
                    difficult = "hard",
                    difficultLevel = 3,
                    amountPeopleServes = 6,
                ),
            createdAt = Date(),
        )

    val recipeEntity =
        RecipeEntity(
            id = "abebfb13-f073-4b99-8c99-deeb9bfbb207",
            name = "Salad",
            description = "A delicious salad",
            amountCalories = "100 kcal",
            amountCarbs = "20g",
            amountProteins = "30g",
            prepTime = "40 min",
            difficult = "hard",
            difficultLevel = 3,
            amountPeopleServes = 6,
            isFavorite = true,
            createdAt = Date().time,
        )

    val preference =
        RecipePreference(
            responseLanguage = "English",
            meal = "Dinner",
            favoriteIngredients = listOf(ingredientModel.name),
            nonFavoriteIngredients = emptyList(),
            intolerantIngredients = emptyList(),
            allergicIngredients = emptyList(),
        )
}
