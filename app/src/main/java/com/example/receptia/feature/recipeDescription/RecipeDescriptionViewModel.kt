package com.example.receptia.feature.recipeDescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.recipeDescription.state.RecipeUiState
import com.example.receptia.feature.recipeDescription.state.ToogleRecipeState
import com.example.receptia.persistence.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RecipeDescriptionViewModel(val recipeId: String) : ViewModel() {
    private val _toogleRecipeState = MutableStateFlow<ToogleRecipeState>(ToogleRecipeState.DetailsSelected)
    val toogleRecipeState get() = _toogleRecipeState

    private val _recipeUiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val recipeUiState: StateFlow<RecipeUiState> = _recipeUiState

    fun selectRecipeToogle() {
        viewModelScope.launch {
            _toogleRecipeState.value = when (_toogleRecipeState.value) {
                ToogleRecipeState.DetailsSelected -> ToogleRecipeState.RecipeSelected
                ToogleRecipeState.RecipeSelected -> ToogleRecipeState.DetailsSelected
            }
        }
    }

    fun toogleFavorite() {
        viewModelScope.launch {
            Recipe.toogleIsFavorite(id = recipeId)
            val recipe = Recipe.find(recipeId)

            _recipeUiState.value = RecipeUiState.Success(recipe = recipe)
        }
    }

    init {
        viewModelScope.launch {
            flow<RecipeUiState> {
                val recipe = Recipe.find(recipeId)
                emit(RecipeUiState.Success(recipe = recipe))
            }.collect {
                _recipeUiState.value = it
            }
        }
    }
}
