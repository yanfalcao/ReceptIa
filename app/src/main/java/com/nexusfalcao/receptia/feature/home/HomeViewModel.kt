package com.nexusfalcao.receptia.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.receptia.feature.home.state.RecipeFeedUiState
import com.nexusfalcao.receptia.persistence.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _lastRecipesUiState = MutableStateFlow<RecipeFeedUiState>(RecipeFeedUiState.Loading)
    val lastRecipesUiState: StateFlow<RecipeFeedUiState> = _lastRecipesUiState

    fun updateLastRecipes() {
        viewModelScope.launch {
            _lastRecipesUiState.value = RecipeFeedUiState.Loading
            val recipeList = Recipe.find(limit = 10)

            _lastRecipesUiState.value = RecipeFeedUiState.Success(recipes = recipeList)
        }
    }
}
