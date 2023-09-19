package com.example.receptia.feature.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.historic.state.RecipeHistoricUiState
import com.example.receptia.persistence.Recipe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class HistoricViewModel : ViewModel() {
    val recipeHistoricState: StateFlow<RecipeHistoricUiState> =
        flow<RecipeHistoricUiState> {
            val recipeList = Recipe.find()

            emit(RecipeHistoricUiState.Success(recipes = recipeList))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecipeHistoricUiState.Loading,
        )
}
