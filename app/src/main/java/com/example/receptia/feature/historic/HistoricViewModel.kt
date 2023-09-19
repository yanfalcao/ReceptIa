package com.example.receptia.feature.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.historic.state.RecipeHistoricUiState
import com.example.receptia.persistence.Recipe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class HistoricViewModel : ViewModel() {
    val recipeHistoricState: StateFlow<RecipeHistoricUiState> =
        flow<RecipeHistoricUiState> {
            delay(2000)

            emit(RecipeHistoricUiState.Success(recipes = listOf()))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecipeHistoricUiState.Loading,
        )

    private fun RecipeMock(name: String = "Espaguete com Molho de Cogumelos e Bacon"): Recipe {
        return Recipe().apply {
            this.name = name
            prepTime = "30 min"
            easeRecipe = "Fácil"
            isFavorite = true
            amountCalories = "450 kcal"
            amountCarbs = "60g"
            amountProteins = "15g"
            amountPeopleServes = 2
        }
    }
}
