package com.example.receptia.feature.newRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.newRecipe.state.*
import com.example.receptia.model.RecipePreferences
import com.example.receptia.network.model.GptFuncitonCallRequest
import com.example.receptia.network.model.GptFunctions
import com.example.receptia.network.model.GptRequest
import com.example.receptia.network.model.GtpMessage
import com.example.receptia.persistence.Recipe
import com.example.receptia.persistence.utils.RecipeDeserializer
import com.example.receptia.repository.RecipeRepository
import com.example.receptia.utils.RequestMessageUtil
import com.google.gson.GsonBuilder
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
class NewRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
) : ViewModel() {
    private val INGREDIENT_LIMIT = 30
    private val recipePreferences = RecipePreferences()
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
                        ingredients = recipePreferences.favoriteIngredients,
                        ingredientsState = _favoriteIngredientsState,
                        isAdd = false,
                    )
                }
                RecipeFieldState.NON_FAVORITE -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredients = recipePreferences.nonFavoriteIngredients,
                        ingredientsState = _nonFavoriteIngredientsState,
                        isAdd = false,
                    )
                }
                RecipeFieldState.ALLERGIC -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredients = recipePreferences.allergicIngredients,
                        ingredientsState = _allergicIngredientsState,
                        isAdd = false,
                    )
                }
                RecipeFieldState.INTOLERANT -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredients = recipePreferences.intolerantIngredients,
                        ingredientsState = _intolerantIngredientsState,
                        isAdd = false,
                    )
                }
                RecipeFieldState.MEAL -> {}
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
                        ingredients = recipePreferences.favoriteIngredients,
                        ingredientsState = _favoriteIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.NON_FAVORITE -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredients = recipePreferences.nonFavoriteIngredients,
                        ingredientsState = _nonFavoriteIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.ALLERGIC -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredients = recipePreferences.allergicIngredients,
                        ingredientsState = _allergicIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.INTOLERANT -> {
                    addOrRemoveIngredient(
                        recipeFieldState = recipeFieldState,
                        text = text,
                        ingredients = recipePreferences.intolerantIngredients,
                        ingredientsState = _intolerantIngredientsState,
                        isAdd = true,
                    )
                }
                RecipeFieldState.MEAL -> {
                    recipePreferences.meal = text
                    _radioUiState.value = RadioUiState.Selected(textOption = text)
                }
            }
        }
    }

    fun createRecipe() {
        viewModelScope.launch {
            if (checkFieldUiState.value is CheckFieldUiState.Filled) {
                _createRecipeUiState.value = CreateRecipeUiState.Loading
                recipePreferences.responseLanguage = Locale.getDefault().displayLanguage
                val request = getChatCompletionRequest(recipePreferences)

                try {
                    val data = repository.createChatCompletion(request)

                    val customDeserializer = GsonBuilder()
                        .registerTypeAdapter(Recipe::class.java, RecipeDeserializer())
                        .create()

                    val recipe = customDeserializer.fromJson(
                        data.choices[0].message.functionCall.arguments,
                        Recipe::class.java,
                    )
                    recipe.create()

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
        ingredients: MutableList<String>,
        ingredientsState: MutableStateFlow<IngredientUiState>,
        isAdd: Boolean,
    ) {
        checkIngredientLimit()

        if (isAdd) {
            if (isMaxIngredientsLimit.value is ErrorUiState.None) {
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
        val countIngredients = recipePreferences.favoriteIngredients.size +
            recipePreferences.intolerantIngredients.size +
            recipePreferences.nonFavoriteIngredients.size +
            recipePreferences.allergicIngredients.size

        _isMaxIngredientsLimit.value = if (countIngredients >= INGREDIENT_LIMIT) {
            ErrorUiState.IngredientMaxLimit
        } else {
            ErrorUiState.None
        }
    }

    private fun getChatCompletionRequest(preferences: RecipePreferences): GptRequest {
        return GptRequest(
            messages = listOf(
                GtpMessage(
                    role = "system",
                    content = RequestMessageUtil.systemContent,
                ),
                GtpMessage(
                    role = "user",
                    content = RequestMessageUtil.newRecipePrompt(preferences),
                ),
            ),
            functions = listOf(
                GptFunctions(
                    name = "get_recipe",
                    parameters = RequestMessageUtil.schema,
                )
            ),
            functionCall = GptFuncitonCallRequest(name = "get_recipe")
        )
    }
}
