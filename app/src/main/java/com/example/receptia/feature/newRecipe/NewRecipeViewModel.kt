package com.example.receptia.feature.newRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.newRecipe.state.*
import com.example.receptia.model.RecipePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewRecipeViewModel : ViewModel() {
    private val recipePreferences = RecipePreferences()

    private val _radioUiState = MutableStateFlow<RadioUiState>(RadioUiState.Unselected)
    val radioUiState get() = _radioUiState

    private val _favoriteIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = IngredientState.FAVORITE,
        ),
    )
    val favoriteIngredientsState get() = _favoriteIngredientsState

    private val _nonFavoriteIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = IngredientState.NON_FAVORITE,
        ),
    )
    val nonFavoriteIngredientsState get() = _nonFavoriteIngredientsState

    private val _allergicIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = IngredientState.ALLERGIC,
        ),
    )
    val allergicIngredientsState get() = _allergicIngredientsState

    private val _intolerantIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = IngredientState.INTOLERANT,
        ),
    )
    val intolerantIngredientsState get() = _intolerantIngredientsState

    fun selectRadio(text: String) {
        viewModelScope.launch {
            recipePreferences.meal = text
            _radioUiState.value = RadioUiState.Selected(textOption = text)
        }
    }

    fun removeIngredient(ingredientState: IngredientState, text: String) {
        viewModelScope.launch {
            when (ingredientState) {
                IngredientState.FAVORITE -> {
                    recipePreferences.favoriteIngredients.remove(text)
                    _favoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.favoriteIngredients.toList(),
                        state = ingredientState,
                    )
                }
                IngredientState.NON_FAVORITE -> {
                    recipePreferences.nonFavoriteIngredients.remove(text)
                    _nonFavoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.nonFavoriteIngredients.toList(),
                        state = ingredientState,
                    )
                }
                IngredientState.ALLERGIC -> {
                    recipePreferences.allergicIngredients.remove(text)
                    _allergicIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.allergicIngredients.toList(),
                        state = ingredientState,
                    )
                }
                IngredientState.INTOLERANT -> {
                    recipePreferences.intolerantIngredients.remove(text)
                    _intolerantIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.intolerantIngredients.toList(),
                        state = ingredientState,
                    )
                }
            }
        }
    }
    fun updateIngredient(ingredientState: IngredientState, text: String) {
        viewModelScope.launch {
            when (ingredientState) {
                IngredientState.FAVORITE -> {
                    recipePreferences.favoriteIngredients.add(text)
                    _favoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.favoriteIngredients.toList(),
                        state = ingredientState,
                    )
                }
                IngredientState.NON_FAVORITE -> {
                    recipePreferences.nonFavoriteIngredients.add(text)
                    _nonFavoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.nonFavoriteIngredients.toList(),
                        state = ingredientState,
                    )
                }
                IngredientState.ALLERGIC -> {
                    recipePreferences.allergicIngredients.add(text)
                    _allergicIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.allergicIngredients.toList(),
                        state = ingredientState,
                    )
                }
                IngredientState.INTOLERANT -> {
                    recipePreferences.intolerantIngredients.add(text)
                    _intolerantIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.intolerantIngredients.toList(),
                        state = ingredientState,
                    )
                }
            }
        }
    }
}
