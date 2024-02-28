package com.nexusfalcao.receptia.feature.recipeDescription

import dagger.assisted.AssistedFactory

@AssistedFactory
interface RecipeDescriptionVMFactory {
    fun create(recipeId: String): RecipeDescriptionViewModel
}
