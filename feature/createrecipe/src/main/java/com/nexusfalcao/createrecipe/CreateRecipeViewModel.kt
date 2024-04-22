package com.nexusfalcao.createrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.CreateRecipeUiState
import com.nexusfalcao.createrecipe.state.ErrorUiState
import com.nexusfalcao.createrecipe.state.IngredientUiState
import com.nexusfalcao.createrecipe.state.RadioUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.model.RecipePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val INGREDIENT_LIMIT = 30
    private val continueButtonClicked = MutableStateFlow(false)

    private val _radioUiState = MutableStateFlow<RadioUiState>(RadioUiState.Unselected)
    val radioUiState get() = _radioUiState

    private val _ingredientsState = MutableStateFlow(IngredientUiState())
    val ingredientsState get() = _ingredientsState

    private val _createRecipeUiState = MutableStateFlow<CreateRecipeUiState>(CreateRecipeUiState.None)
    val createRecipeUiState get() = _createRecipeUiState

    private val _isMaxIngredientsLimit = MutableStateFlow<ErrorUiState>(ErrorUiState.None)
    val isMaxIngredientsLimit get() = _isMaxIngredientsLimit

    val checkFieldUiState = combine(
        radioUiState,
        ingredientsState,
        continueButtonClicked,
    ) { radioUiState, ingredientsState, continueButtonClicked ->
        val favoriteIngredientsList = ingredientsState.favoriteIngredients
        if (radioUiState is RadioUiState.Selected && favoriteIngredientsList.isNotEmpty()) {
            CheckFieldUiState.Filled
        } else {
            if (continueButtonClicked) {
                val fields = mutableListOf<RecipeFieldState>()
                if (radioUiState is RadioUiState.Unselected) {
                    fields.add(RecipeFieldState.MEAL)
                }
                if (favoriteIngredientsList.isEmpty()) {
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

    fun removePreference(recipeFieldState: RecipeFieldState, text: String) {
        viewModelScope.launch {
            val ingredients = _ingredientsState.value.copy()

            ingredients.removeIngredient(recipeFieldState, text)
            changeMaxIngredientState(ingredients)
            _ingredientsState.value = ingredients
        }
    }
    fun addPreference(recipeFieldState: RecipeFieldState, text: String) {
        viewModelScope.launch {
            if(recipeFieldState is RecipeFieldState.MEAL){
                _radioUiState.value = RadioUiState.Selected(text)
            } else {
                val ingredients = _ingredientsState.value.copy()

                if (!isIngredientLimitReached(ingredients) && text.isNotEmpty() && text.isNotBlank()) {
                    ingredients.addIngredient(recipeFieldState, text)
                    _ingredientsState.value = ingredients
                } else {
                    changeMaxIngredientState(ingredients)
                }
            }
        }
    }

    fun createRecipe(chatGptApiModel: String) {
        viewModelScope.launch {
            _isMaxIngredientsLimit.value = ErrorUiState.None

            if (checkFieldUiState.value is CheckFieldUiState.Filled) {
                _createRecipeUiState.value = CreateRecipeUiState.Loading
                try {
                    val meal = (_radioUiState.value as RadioUiState.Selected).textOption
                    val preference = RecipePreference(
                        favoriteIngredients = _ingredientsState.value.favoriteIngredients,
                        nonFavoriteIngredients = _ingredientsState.value.nonFavoritesIngredients,
                        intolerantIngredients = _ingredientsState.value.intolerantIngredients,
                        allergicIngredients = _ingredientsState.value.allergicingredients,
                        meal = meal,
                        responseLanguage = Locale.getDefault().displayLanguage
                    )

                    val recipes = recipeRepository.callNewRecipe(
                        preference = preference,
                        apiModel = chatGptApiModel
                    )

                    val recipe = recipes[0]
                    recipeRepository.insertRecipe(recipe)

                    _createRecipeUiState.value = CreateRecipeUiState.Success(recipe.id)
                } catch (e: Exception) {
                    e.printStackTrace()
                    _createRecipeUiState.value = CreateRecipeUiState.Error
                }
            } else {
                continueButtonClicked.value = true
            }
        }
    }

    fun cleanCreateRecipeUiState() {
        viewModelScope.launch {
            _createRecipeUiState.value = CreateRecipeUiState.None
        }
    }

    private fun isIngredientLimitReached(ingredients: IngredientUiState): Boolean {
        val countIngredients = ingredients.favoriteIngredients.size +
                ingredients.intolerantIngredients.size +
                ingredients.allergicingredients.size +
                ingredients.nonFavoritesIngredients.size

        return countIngredients >= INGREDIENT_LIMIT
    }

    private fun changeMaxIngredientState(ingredients: IngredientUiState) {
        if (isIngredientLimitReached(ingredients)) {
            _isMaxIngredientsLimit.value = ErrorUiState.IngredientMaxLimit
        } else {
            _isMaxIngredientsLimit.value = ErrorUiState.None
        }
    }
}
