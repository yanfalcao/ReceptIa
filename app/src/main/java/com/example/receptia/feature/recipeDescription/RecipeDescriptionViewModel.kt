package com.example.receptia.feature.recipeDescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.recipeDescription.state.RecipeUiState
import com.example.receptia.feature.recipeDescription.state.ToogleRecipeState
import com.example.receptia.persistence.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDescriptionViewModel(val recipeId: String) : ViewModel() {
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

    val getRecipe: StateFlow<RecipeUiState> =
        flow<RecipeUiState> {
            val recipe = Recipe.find(recipeId)

            emit(RecipeUiState.Success(recipe = recipe))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecipeUiState.Loading,
        )
}
