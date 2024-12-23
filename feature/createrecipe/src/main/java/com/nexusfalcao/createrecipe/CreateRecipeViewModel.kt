package com.nexusfalcao.createrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.CreateRecipeUiState
import com.nexusfalcao.createrecipe.state.ErrorUiState
import com.nexusfalcao.createrecipe.state.FieldsUiState
import com.nexusfalcao.createrecipe.state.RadioUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.model.RecipePreference
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.Exception

@HiltViewModel(assistedFactory = CreateRecipeViewModel.Factory::class)
class CreateRecipeViewModel
    @AssistedInject
    constructor(
        private val recipeRepository: RecipeRepository,
        @Assisted private val crashlytics: FirebaseCrashlytics,
    ) : ViewModel() {
        @AssistedFactory interface Factory {
            fun create(crashlytics: FirebaseCrashlytics): CreateRecipeViewModel
        }

        private val CHAR_LIMIT = 750

        private val _hasCriationTries = MutableStateFlow(false)

        private val _fieldsUiState = MutableStateFlow(FieldsUiState())
        val fieldsUiState get() = _fieldsUiState

        private val _createRecipeUiState = MutableStateFlow<CreateRecipeUiState>(CreateRecipeUiState.None)
        val createRecipeUiState get() = _createRecipeUiState

        private val _errorUiState = MutableStateFlow<ErrorUiState>(ErrorUiState.None)
        val errorUiState get() = _errorUiState

        val checkFieldUiState =
            combine(
                fieldsUiState,
                _hasCriationTries,
            ) { fieldsUiState, hasCriationTries ->
                val favoriteIngredientsList = fieldsUiState.favoriteIngredients
                val nonFavoriteIngredientsList = fieldsUiState.nonFavoritesIngredients
                val intolerantIngredientsList = fieldsUiState.intolerantIngredients
                val allergicIngredientsList = fieldsUiState.allergicingredients

                if (fieldsUiState.meal is RadioUiState.Selected && favoriteIngredientsList.isNotEmpty()) {
                    CheckFieldUiState.Filled
                } else {
                    // Just throw the error if the user has already tried to create a recipe
                    if (hasCriationTries) {
                        val fields =
                            mutableListOf<RecipeFieldState>().apply {
                                if (fieldsUiState.meal is RadioUiState.Unselected) add(RecipeFieldState.MEAL)
                                if (favoriteIngredientsList.isEmpty()) add(RecipeFieldState.FAVORITE)
                                if (nonFavoriteIngredientsList.isEmpty()) add(RecipeFieldState.NON_FAVORITE)
                                if (intolerantIngredientsList.isEmpty()) add(RecipeFieldState.INTOLERANT)
                                if (allergicIngredientsList.isEmpty()) add(RecipeFieldState.ALLERGIC)
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

        fun removePreference(
            recipeFieldState: RecipeFieldState,
            text: String,
        ) {
            viewModelScope.launch {
                val fields = _fieldsUiState.value.copy()

                fields.removeField(recipeFieldState, text)
                changeErrorUiState(fields)
                _fieldsUiState.value = fields
            }
        }

        fun addPreference(
            recipeFieldState: RecipeFieldState,
            text: String,
        ) {
            viewModelScope.launch {
                val fieldsUiStateCopy = _fieldsUiState.value.copy()
                fieldsUiStateCopy.addField(recipeFieldState, text)

                if (recipeFieldState is RecipeFieldState.MEAL) {
                    _fieldsUiState.value = fieldsUiStateCopy
                } else {
                    if (!isCharLimitReached(fieldsUiStateCopy) && text.isNotEmpty() && text.isNotBlank()) {
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

                if (checkFieldUiState.value !is CheckFieldUiState.Filled) {
                    _hasCriationTries.value = true
                    return@launch
                }

                _createRecipeUiState.value = CreateRecipeUiState.Loading
                try {
                    val preference = createRecipePreference()
                    val recipes = recipeRepository.callNewRecipe(preference, chatGptApiModel)

                    recipeRepository.insertRecipe(recipes[0])

                    _createRecipeUiState.value = CreateRecipeUiState.Success(recipes[0].id)
                } catch (e: Exception) {
                    crashlytics.recordException(e)
                    _createRecipeUiState.value = CreateRecipeUiState.Error
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
            val ingredientsStringSize =
                fieldState.getListStringSize(RecipeFieldState.FAVORITE) +
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

        private fun createRecipePreference(): RecipePreference {
            val fieldsUiState = _fieldsUiState.value

            return RecipePreference(
                favoriteIngredients = fieldsUiState.favoriteIngredients,
                nonFavoriteIngredients = fieldsUiState.nonFavoritesIngredients,
                intolerantIngredients = fieldsUiState.intolerantIngredients,
                allergicIngredients = fieldsUiState.allergicingredients,
                meal = (fieldsUiState.meal as RadioUiState.Selected).textOption,
                responseLanguage = Locale.getDefault().displayLanguage,
            )
        }
    }
