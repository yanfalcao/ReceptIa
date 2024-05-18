package com.nexusfalcao.data.extensions

import com.nexusfalcao.data.util.ModelInstance
import org.junit.Assert
import org.junit.Test

class IngredientExtensionsTest {
    @Test
    fun `check if ingredient entity to ingredient model conversion is correct`() {
        val ingredientEntity = ModelInstance.ingredientEntity

        val result = ingredientEntity.asIngredientModel()

        Assert.assertEquals(ingredientEntity.id, result.id)
        Assert.assertEquals(ingredientEntity.name, result.name)
        Assert.assertEquals(ingredientEntity.measure, result.measure)
    }

    @Test
    fun `check if ingredient model to ingredient entity conversion is correct`() {
        val ingredient = ModelInstance.ingredientModel
        val recipeId = "recipeId"

        val result = ingredient.asIngredientEntity(recipeId)

        Assert.assertEquals(ingredient.id, result.id)
        Assert.assertEquals(ingredient.name, result.name)
        Assert.assertEquals(ingredient.measure, result.measure)
        Assert.assertEquals(recipeId, result.recipeId)
    }
}
