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

    private val _createRecipeUiState = MutableStateFlow<CreateRecipeUiState>(CreateRecipeUiState.None)
    val createRecipeUiState get() = _createRecipeUiState

    private val _isMaxIngredientsLimit = MutableStateFlow<ErrorUiState>(ErrorUiState.None)
    val isMaxIngredientsLimit get() = _isMaxIngredientsLimit

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

    fun removePreference(recipeFieldState: RecipeFieldState, text: String) {
        viewModelScope.launch {
            when (recipeFieldState) {
                RecipeFieldState.FAVORITE -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _favoriteIngredientsState,
                        isAdd = false,
                    )
                }
                RecipeFieldState.NON_FAVORITE -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _nonFavoriteIngredientsState,
                        isAdd = false,
                    )
                }
                RecipeFieldState.ALLERGIC -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _allergicIngredientsState,
                        isAdd = false,
                    )
                }
                RecipeFieldState.INTOLERANT -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _intolerantIngredientsState,
                        isAdd = false,
                    )
                }
                else -> {}
            }
        }
    }
    fun addPreference(recipeFieldState: RecipeFieldState, text: String) {
        viewModelScope.launch {
            when (recipeFieldState) {
                RecipeFieldState.FAVORITE -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _favoriteIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.NON_FAVORITE -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _nonFavoriteIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.ALLERGIC -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _allergicIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.INTOLERANT -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredientsState = _intolerantIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.MEAL -> {
                    _radioUiState.value = RadioUiState.Selected(textOption = text)
                }
            }
        }
    }

    fun createRecipe(chatGptApiModel: String) {
        viewModelScope.launch {
            if (checkFieldUiState.value is CheckFieldUiState.Filled) {
                _createRecipeUiState.value = CreateRecipeUiState.Loading
                try {
                    val meal = (_radioUiState.value as RadioUiState.Selected).textOption
                    val preference = RecipePreference(
                        favoriteIngredients = _favoriteIngredientsState.value.ingredients,
                        nonFavoriteIngredients = _nonFavoriteIngredientsState.value.ingredients,
                        intolerantIngredients = _intolerantIngredientsState.value.ingredients,
                        allergicIngredients = _allergicIngredientsState.value.ingredients,
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

    private fun addOrRemoveIngredient(
        recipeFieldState: RecipeFieldState,
        text: String,
        ingredientsState: MutableStateFlow<IngredientUiState>,
        isAdd: Boolean,
    ) {
        checkIngredientLimit()

        val ingredients = ingredientsState.value.ingredients.toMutableList()

        if (isAdd) {
            if (isMaxIngredientsLimit.value is ErrorUiState.None
                && text.isNotEmpty()
                && text.isNotBlank()
            ) {
                ingredients.add(text)
            }
        } else {
            ingredients.remove(text)
        }

        ingredientsState.value = IngredientUiState(
            ingredients = ingredients.toList(),
            state = recipeFieldState,
        )
    }

    private fun checkIngredientLimit() {
        val countIngredients = _favoriteIngredientsState.value.ingredients.size +
            _intolerantIngredientsState.value.ingredients.size +
            _nonFavoriteIngredientsState.value.ingredients.size +
            _allergicIngredientsState.value.ingredients.size

        _isMaxIngredientsLimit.value = if (countIngredients >= INGREDIENT_LIMIT) {
            ErrorUiState.IngredientMaxLimit
        } else {
            ErrorUiState.None
        }
    }
}
