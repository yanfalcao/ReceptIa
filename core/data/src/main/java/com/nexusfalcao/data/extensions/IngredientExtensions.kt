package com.nexusfalcao.data.extensions

import com.nexusfalcao.database.model.IngredientEntity
import com.nexusfalcao.model.Ingredient

internal fun IngredientEntity.asIngredientModel() = Ingredient(
    id = this.id,
    name = this.name,
    measure = this.measure,
)

internal fun Ingredient.asIngredientEntity(idRecipe: String) = IngredientEntity(
    id = this.id,
    name = this.name,
    measure = this.measure,
    recipeId = idRecipe,
)