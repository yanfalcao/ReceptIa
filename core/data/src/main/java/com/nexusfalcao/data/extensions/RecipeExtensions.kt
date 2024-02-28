package com.nexusfalcao.data.extensions

import com.nexusfalcao.database.model.RecipeEntity
import com.nexusfalcao.database.model.RecipeWithRelations
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.Step
import java.util.Date

internal fun RecipeEntity.asRecipeModel(
    ingredientsModel: List<Ingredient>,
    stepsModel: List<Step>
) = Recipe(
    id = this.id,
    name = this.name,
    description = this.description,
    ingredients = ingredientsModel,
    recipeSteps = stepsModel,
    isFavorite = this.isFavorite,
    recipeDetails = RecipeDetails(
        amountCalories = this.amountCalories,
        amountCarbs = this.amountCarbs,
        amountProteins = this.amountProteins,
        preparationTime = this.prepTime,
        difficult = this.difficult,
        difficultLevel = this.difficultLevel,
        amountPeopleServes = this.amountPeopleServes,
    ),
    createdAt = Date(this.createdAt)
)

internal fun Recipe.asRecipeEntity() = RecipeEntity(
    id = this.id,
    name = this.name,
    description = this.description,
    amountCalories = this.recipeDetails.amountCalories,
    amountCarbs = this.recipeDetails.amountCarbs,
    amountProteins = this.recipeDetails.amountProteins,
    prepTime = this.recipeDetails.preparationTime,
    difficult = this.recipeDetails.difficult,
    difficultLevel = this.recipeDetails.difficultLevel,
    amountPeopleServes = this.recipeDetails.amountPeopleServes,
    isFavorite = this.isFavorite,
    createdAt = this.createdAt.time,
)

internal fun RecipeWithRelations.asRecipeEntity(): Recipe {
    val ingredients = this.ingredients.map { item ->
        item.asIngredientModel()
    }
    val steps = this.steps.map { item ->
        item.asStepModel()
    }

    return this.recipe.asRecipeModel(ingredients, steps)
}