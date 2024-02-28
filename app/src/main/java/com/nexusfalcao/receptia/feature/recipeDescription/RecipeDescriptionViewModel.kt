package com.nexusfalcao.receptia.feature.recipeDescription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.receptia.feature.recipeDescription.state.RecipeUiState
import com.nexusfalcao.receptia.feature.recipeDescription.state.ToogleRecipeState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RecipeDescriptionVMFactory::class)
class RecipeDescriptionViewModel @AssistedInject constructor(
    @Assisted val recipeId: String,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _toogleRecipeState = MutableStateFlow<ToogleRecipeState>(ToogleRecipeState.DetailsSelected)
    val toogleRecipeState get() = _toogleRecipeState

    private val _recipeUiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val recipeUiState: StateFlow<RecipeUiState> = _recipeUiState

    fun selectRecipeToogle() {
        viewModelScope.launch {
            _toogleRecipeState.value = when (_toogleRecipeState.value) {
                ToogleRecipeState.DetailsSelected -> ToogleRecipeState.RecipeSelected
                ToogleRecipeState.RecipeSelected -> ToogleRecipeState.DetailsSelected
            }
        }
    }

    fun toogleFavorite() {
        viewModelScope.launch {
            recipeRepository.findRecipe(recipeId)?.let {
                it.toogleIsFavorite()
                recipeRepository.updateIsFavorite(recipeId, it.isFavorite)

                _recipeUiState.value = RecipeUiState.Success(recipe = it)
            }
        }
    }

    init {
        viewModelScope.launch {
            flow<RecipeUiState> {
                recipeRepository.findRecipe(recipeId)?.let {
                    emit(RecipeUiState.Success(recipe = it))
                }
            }.collect {
                _recipeUiState.value = it
            }
        }
    }
}
