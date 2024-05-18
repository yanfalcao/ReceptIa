package com.nexusfalcao.data.extensions

import com.nexusfalcao.data.util.ModelInstance
import org.junit.Assert
import org.junit.Test

class StepExtensionsTest {
    @Test
    fun `check if step entity to step model conversion is correct`() {
        val stepEntity = ModelInstance.stepEntity

        val step = stepEntity.asStepModel()

        Assert.assertEquals(stepEntity.id, step.id)
        Assert.assertEquals(stepEntity.position, step.position)
        Assert.assertEquals(stepEntity.description, step.description)
    }

    @Test
    fun `step model to step entity conversion is correct`() {
        val step = ModelInstance.stepModel
        val idRecipe = "recipeId"

        val stepEntity = step.asStepEntity(idRecipe)

        Assert.assertEquals(step.id, stepEntity.id)
        Assert.assertEquals(step.position, stepEntity.position)
        Assert.assertEquals(step.description, stepEntity.description)
        Assert.assertEquals(idRecipe, stepEntity.recipeId)
    }
}
