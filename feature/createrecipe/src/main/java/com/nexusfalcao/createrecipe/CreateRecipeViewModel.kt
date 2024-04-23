package com.nexusfalcao.createrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.CreateRecipeUiState
import com.nexusfalcao.createrecipe.state.ErrorUiState
import com.nexusfalcao.createrecipe.state.FieldsUiState
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
    private val CHAR_LIMIT = 750
    private val continueButtonClicked = MutableStateFlow(false)

    private val _fieldsUiState = MutableStateFlow(FieldsUiState())
    val fieldsUiState get() = _fieldsUiState

    private val _createRecipeUiState = MutableStateFlow<CreateRecipeUiState>(CreateRecipeUiState.None)
    val createRecipeUiState get() = _createRecipeUiState

    private val _errorUiState = MutableStateFlow<ErrorUiState>(ErrorUiState.None)
    val errorUiState get() = _errorUiState

    val checkFieldUiState = combine(
        fieldsUiState,
        continueButtonClicked,
    ) { fieldsUiState, continueButtonClicked ->
        val favoriteIngredientsList = fieldsUiState.favoriteIngredients
        if (fieldsUiState.meal is RadioUiState.Selected && favoriteIngredientsList.isNotEmpty()) {
            CheckFieldUiState.Filled
        } else {
            if (continueButtonClicked) {
                val fields = mutableListOf<RecipeFieldState>()
                if (fieldsUiState.meal is RadioUiState.Unselected) {
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
            val fields = _fieldsUiState.value.copy()

            fields.removeIngredient(recipeFieldState, text)
            changeErrorUiState(fields)
            _fieldsUiState.value = fields
        }
    }
    fun addPreference(recipeFieldState: RecipeFieldState, text: String) {
        viewModelScope.launch {
            val fieldsUiStateCopy = _fieldsUiState.value.copy()

            if(recipeFieldState is RecipeFieldState.MEAL){
                fieldsUiStateCopy.addField(recipeFieldState, text)
                _fieldsUiState.value = fieldsUiStateCopy
            } else {
                if (!isCharLimitReached(fieldsUiStateCopy) && text.isNotEmpty() && text.isNotBlank()) {
                    fieldsUiStateCopy.addField(recipeFieldState, text)
                    _fieldsUiState.value = fieldsUiStateCopy
                } else {
                    changeErrorUiState(fieldsUiStateCopy)
                }
            }
        }
    }

    fun createRecipe(chatGptApiModel: String) {
        viewModelScope.launch {
            _errorUiState.value = ErrorUiState.None

            if (checkFieldUiState.value is CheckFieldUiState.Filled) {
                _createRecipeUiState.value = CreateRecipeUiState.Loading
                try {
                    val meal = (_fieldsUiState.value.meal as RadioUiState.Selected).textOption
                    val preference = RecipePreference(
                        favoriteIngredients = _fieldsUiState.value.favoriteIngredients,
                        nonFavoriteIngredients = _fieldsUiState.value.nonFavoritesIngredients,
                        intolerantIngredients = _fieldsUiState.value.intolerantIngredients,
                        allergicIngredients = _fieldsUiState.value.allergicingredients,
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

    /***
     * Check if the character limit has been reached in all the ingredients fields
     */
    private fun isCharLimitReached(fieldState: FieldsUiState): Boolean {
        val ingredientsStringSize = fieldState.getListStringSize(RecipeFieldState.FAVORITE) +
                fieldState.getListStringSize(RecipeFieldState.NON_FAVORITE) +
                fieldState.getListStringSize(RecipeFieldState.ALLERGIC) +
                fieldState.getListStringSize(RecipeFieldState.INTOLERANT)

        return ingredientsStringSize >= CHAR_LIMIT
    }

    private fun changeErrorUiState(ingredients: FieldsUiState) {
        if (isCharLimitReached(ingredients)) {
            _errorUiState.value = ErrorUiState.IngredientMaxLimit
        } else {
            _errorUiState.value = ErrorUiState.None
        }
    }
}
