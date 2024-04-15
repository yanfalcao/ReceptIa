package com.nexusfalcao.description

import dagger.assisted.AssistedFactory

@AssistedFactory
interface RecipeDescriptionVMFactory {
    fun create(recipeId: String): RecipeDescriptionViewModel
}
