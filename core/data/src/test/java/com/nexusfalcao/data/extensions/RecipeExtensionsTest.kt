package com.nexusfalcao.data.extensions

import com.nexusfalcao.data.util.ModelInstance
import org.junit.Assert
import org.junit.Test

class RecipeExtensionsTest {
    @Test
    fun `check if recipe entity to recipe model conversion is correct`() {
        val recipeEntity = ModelInstance.recipeEntity
        val ingredientsModel = ModelInstance.ingredientModel
        val stepModel = ModelInstance.stepModel

        val result = recipeEntity.asRecipeModel(listOf(ingredientsModel), listOf(stepModel))

        Assert.assertEquals(recipeEntity.id, result.id)
        Assert.assertEquals(recipeEntity.name, result.name)
        Assert.assertEquals(recipeEntity.description, result.description)
        Assert.assertEquals(listOf(ingredientsModel), result.ingredients)
        Assert.assertEquals(listOf(stepModel), result.recipeSteps)
        Assert.assertEquals(recipeEntity.isFavorite, result.isFavorite)
        Assert.assertEquals(recipeEntity.amountCalories, result.recipeDetails.amountCalories)
        Assert.assertEquals(recipeEntity.amountCarbs, result.recipeDetails.amountCarbs)
        Assert.assertEquals(recipeEntity.amountProteins, result.recipeDetails.amountProteins)
        Assert.assertEquals(recipeEntity.prepTime, result.recipeDetails.preparationTime)
        Assert.assertEquals(recipeEntity.difficult, result.recipeDetails.difficult)
        Assert.assertEquals(recipeEntity.difficultLevel, result.recipeDetails.difficultLevel)
        Assert.assertEquals(recipeEntity.amountPeopleServes, result.recipeDetails.amountPeopleServes)
    }

    @Test
    fun `check if recipe model to recipe entity conversion is correct`() {
        val model = ModelInstance.recipeModel
        val result = model.asRecipeEntity()

        Assert.assertEquals(model.id, result.id)
        Assert.assertEquals(model.name, result.name)
        Assert.assertEquals(model.description, result.description)
        Assert.assertEquals(model.recipeDetails.amountCalories, result.amountCalories)
        Assert.assertEquals(model.recipeDetails.amountCarbs, result.amountCarbs)
        Assert.assertEquals(model.recipeDetails.amountProteins, result.amountProteins)
        Assert.assertEquals(model.recipeDetails.preparationTime, result.prepTime)
        Assert.assertEquals(model.recipeDetails.difficult, result.difficult)
        Assert.assertEquals(model.recipeDetails.difficultLevel, result.difficultLevel)
        Assert.assertEquals(model.recipeDetails.amountPeopleServes, result.amountPeopleServes)
        Assert.assertEquals(model.isFavorite, result.isFavorite)
    }
}
