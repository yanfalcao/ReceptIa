package com.nexusfalcao.receptia.feature.recipeDescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecipeDescriptionVMFactory(val recipeId: String) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipeDescriptionViewModel(recipeId) as T
    }
}
