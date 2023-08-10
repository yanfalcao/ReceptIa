package com.example.receptia.feature.newRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.newRecipe.state.*
import com.example.receptia.model.RecipePreferences
import com.example.receptia.network.model.NetworkGptRequest
import com.example.receptia.network.model.NetworkGtpMessage
import com.example.receptia.repository.RecipeRepository
import com.example.receptia.utils.RequestMessageUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRecipeViewModel @Inject constructor(
    val repository: RecipeRepository,
) : ViewModel() {
    private val recipePreferences = RecipePreferences()

    private val _radioUiState = MutableStateFlow<RadioUiState>(RadioUiState.Unselected)
    val radioUiState get() = _radioUiState

    private val _favoriteIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = RecipeFieldState.FAVORITE,
        ),
    )
    val favoriteIngredientsState get() = _favoriteIngredientsState

    private val _nonFavoriteIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = RecipeFieldState.NON_FAVORITE,
        ),
    )
    val nonFavoriteIngredientsState get() = _nonFavoriteIngredientsState

    private val _allergicIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = RecipeFieldState.ALLERGIC,
        ),
    )
    val allergicIngredientsState get() = _allergicIngredientsState

    private val _intolerantIngredientsState = MutableStateFlow(
        IngredientUiState(
            state = RecipeFieldState.INTOLERANT,
        ),
    )
    val intolerantIngredientsState get() = _intolerantIngredientsState

    private val continueButtonClicked = MutableStateFlow(false)

    val checkFieldUiState = combine(
        radioUiState,
        favoriteIngredientsState,
        continueButtonClicked,
    ) { radioUiState, favoriteIngredientsState, continueButtonClicked ->
        if (radioUiState is RadioUiState.Selected &&
            favoriteIngredientsState.ingredients.isNotEmpty()
        ) {
            CheckFieldUiState.Filled
        } else {
            if (continueButtonClicked) {
                val fields = mutableListOf<RecipeFieldState>()
                if (radioUiState is RadioUiState.Unselected) {
                    fields.add(RecipeFieldState.MEAL)
                }
                if (favoriteIngredientsState.ingredients.isEmpty()) {
                    fields.add(RecipeFieldState.FAVORITE)
                }
                CheckFieldUiState.Unfilled(fields)
            } else {
                CheckFieldUiState.None
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(300),
        initialValue = CheckFieldUiState.None,
    )

    fun selectRadio(text: String) {
        viewModelScope.launch {
            recipePreferences.meal = text
            _radioUiState.value = RadioUiState.Selected(textOption = text)
        }
    }

    fun removeIngredient(recipeFieldState: RecipeFieldState, text: String) {
        viewModelScope.launch {
            when (recipeFieldState) {
                RecipeFieldState.FAVORITE -> {
                    recipePreferences.favoriteIngredients.remove(text)
                    _favoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.favoriteIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                RecipeFieldState.NON_FAVORITE -> {
                    recipePreferences.nonFavoriteIngredients.remove(text)
                    _nonFavoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.nonFavoriteIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                RecipeFieldState.ALLERGIC -> {
                    recipePreferences.allergicIngredients.remove(text)
                    _allergicIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.allergicIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                RecipeFieldState.INTOLERANT -> {
                    recipePreferences.intolerantIngredients.remove(text)
                    _intolerantIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.intolerantIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                else -> {}
            }
        }
    }
    fun updateIngredient(recipeFieldState: RecipeFieldState, text: String) {
        viewModelScope.launch {
            when (recipeFieldState) {
                RecipeFieldState.FAVORITE -> {
                    recipePreferences.favoriteIngredients.add(text)
                    _favoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.favoriteIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                RecipeFieldState.NON_FAVORITE -> {
                    recipePreferences.nonFavoriteIngredients.add(text)
                    _nonFavoriteIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.nonFavoriteIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                RecipeFieldState.ALLERGIC -> {
                    recipePreferences.allergicIngredients.add(text)
                    _allergicIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.allergicIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                RecipeFieldState.INTOLERANT -> {
                    recipePreferences.intolerantIngredients.add(text)
                    _intolerantIngredientsState.value = IngredientUiState(
                        ingredients = recipePreferences.intolerantIngredients.toList(),
                        state = recipeFieldState,
                    )
                }
                else -> {}
            }
        }
    }

    fun createRecipe() {
        viewModelScope.launch {
            if (checkFieldUiState.value is CheckFieldUiState.Filled) {
                var request = NetworkGptRequest(
                    messages = listOf(
                        NetworkGtpMessage(
                            role = "user",
                            content = RequestMessageUtil.newRecipePrompt(recipePreferences),
                        ),
                    ),
                )
                val data = repository.getPrompt(request)
                data.toString()
            } else {
                continueButtonClicked.value = true
            }
        }
    }
}
