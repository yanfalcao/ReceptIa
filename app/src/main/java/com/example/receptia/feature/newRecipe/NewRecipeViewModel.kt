package com.example.receptia.feature.newRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.newRecipe.state.IngredientState
import com.example.receptia.feature.newRecipe.state.RadioUiState
import com.example.receptia.model.RecipePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewRecipeViewModel : ViewModel() {
    private val recipePreferences = RecipePreferences()

    private val _radioUiState = MutableStateFlow<RadioUiState>(RadioUiState.Unselected)
    val radioUiState get() = _radioUiState

    private val _favoriteIngredientsState = MutableStateFlow<List<String>>(listOf())
    val favoriteIngredientsState get() = _favoriteIngredientsState

    private val _nonFavoriteIngredientsState = MutableStateFlow<List<String>>(listOf())
    val nonFavoriteIngredientsState get() = _nonFavoriteIngredientsState

    private val _allergicIngredientsState = MutableStateFlow<List<String>>(listOf())
    val allergicIngredientsState get() = _allergicIngredientsState

    private val _intolerantIngredientsState = MutableStateFlow<List<String>>(listOf())
    val intolerantIngredientsState get() = _intolerantIngredientsState

    fun selectRadio(text: String) {
        viewModelScope.launch {
            recipePreferences.meal = text
            _radioUiState.value = RadioUiState.Selected(textOption = text)
        }
    }

    fun updateFavoriteIngredients(ingredientState: IngredientState, text: String) {
        viewModelScope.launch {
            when (ingredientState) {
                IngredientState.FAVORITE -> {
                    recipePreferences.favoriteIngredients.add(text)
                    _favoriteIngredientsState.emit(recipePreferences.favoriteIngredients)
                }
                IngredientState.NON_FAVORITE -> {
                    recipePreferences.nonFavoriteIngredients.add(text)
                    _nonFavoriteIngredientsState.emit(recipePreferences.nonFavoriteIngredients)
                }
                IngredientState.ALLERGIC -> {
                    recipePreferences.allergicIngredients.add(text)
                    _allergicIngredientsState.emit(recipePreferences.allergicIngredients)
                }
                IngredientState.INTOLERANT -> {
                    recipePreferences.intolerantIngredients.add(text)
                    _intolerantIngredientsState.emit(recipePreferences.intolerantIngredients)
                }
            }
        }
    }
}
