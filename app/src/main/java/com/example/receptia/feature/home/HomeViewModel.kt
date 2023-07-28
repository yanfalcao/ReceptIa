package com.example.receptia.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.home.state.RecipeFeedUiState
import com.example.receptia.model.Recipe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {
    // TODO: Implement logic
    val feedState: StateFlow<RecipeFeedUiState> =
        flow<RecipeFeedUiState> {
            delay(10000)
            val recipeList = listOf(
                RecipeMock(),
                RecipeMock(),
                RecipeMock(),
                RecipeMock(),
                RecipeMock(),
                RecipeMock(),
            )

            emit(RecipeFeedUiState.Success(recipes = recipeList))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RecipeFeedUiState.Loading,
        )

    private fun RecipeMock(): Recipe {
        return Recipe(
            id = "1",
            name = "Espaguete com Molho de Cogumelos e Bacon",
            description = "",
            prepTime = "30 min",
            easeRecipe = "Fácil",
            isFavorite = true,
            amountCalories = "450 kcal",
            amountCarbs = "60g",
            amountProteins = "15g",
            amountPeopleServes = 2,
            recipeSteps = "",
            ingredients = listOf(),
        )
    }
}
