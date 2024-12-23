package com.nexusfalcao.description

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDescriptionViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val _toogleRecipeState = MutableStateFlow<ToogleRecipeState>(ToogleRecipeState.DetailsSelected)
    val toogleRecipeState get() = _toogleRecipeState

    private val _recipeUiState = MutableStateFlow<RecipeUiState>(RecipeUiState.Loading)
    val recipeUiState: StateFlow<RecipeUiState> = _recipeUiState

    fun getRecipe(recipeId: String) {
        viewModelScope.launch {
            val recipe = recipeRepository.findRecipe(recipeId) ?: return@launch

            _recipeUiState.value = RecipeUiState.Success(recipe)
        }
    }

    fun selectRecipeToogle() {
        viewModelScope.launch {
            _toogleRecipeState.value = when (_toogleRecipeState.value) {
                ToogleRecipeState.DetailsSelected -> ToogleRecipeState.RecipeSelected
                ToogleRecipeState.RecipeSelected -> ToogleRecipeState.DetailsSelected
            }
        }
    }

    fun toogleFavorite(recipeId: String) {
        viewModelScope.launch {
            recipeRepository.findRecipe(recipeId)?.let {
                it.toogleIsFavorite()
                recipeRepository.updateIsFavorite(recipeId, it.isFavorite)

                _recipeUiState.value = RecipeUiState.Success(recipe = it)
            }
        }
    }
}
