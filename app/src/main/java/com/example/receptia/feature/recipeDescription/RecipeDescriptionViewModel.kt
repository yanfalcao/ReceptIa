package com.example.receptia.feature.recipeDescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.recipeDescription.state.ToogleRecipeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RecipeDescriptionViewModel : ViewModel() {
    private val _toogleRecipeState = MutableStateFlow<ToogleRecipeState>(ToogleRecipeState.DetailsSelected)
    val toogleRecipeState get() = _toogleRecipeState

    fun selectRecipeToogle() {
        viewModelScope.launch {
            _toogleRecipeState.value = when (_toogleRecipeState.value) {
                ToogleRecipeState.DetailsSelected -> ToogleRecipeState.RecipeSelected
                ToogleRecipeState.RecipeSelected -> ToogleRecipeState.DetailsSelected
            }
        }
    }
}
