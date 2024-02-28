package com.nexusfalcao.data.extensions

import com.nexusfalcao.database.model.StepEntity
import com.nexusfalcao.model.Step

internal fun StepEntity.asStepModel() = Step(
    id = this.id,
    position = this.position,
    description = this.description,
)

internal fun Step.asStepEntity(idRecipe: String) = StepEntity(
    id = this.id,
    position = this.position,
    description = this.description,
    recipeId = idRecipe,
)