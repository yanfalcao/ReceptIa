package com.example.receptia.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.home.state.RecipeFeedUiState
import com.example.receptia.persistence.Recipe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {
    val feedState: StateFlow<RecipeFeedUiState> =
        flow<RecipeFeedUiState> {
            val recipeList = Recipe.find(limit = 10)

            emit(RecipeFeedUiState.Success(recipes = recipeList))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecipeFeedUiState.Loading,
        )
}
