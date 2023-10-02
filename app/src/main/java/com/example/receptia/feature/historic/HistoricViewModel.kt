package com.example.receptia.feature.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.historic.state.FilterUiState
import com.example.receptia.feature.historic.state.RecipeHistoricUiState
import com.example.receptia.feature.historic.state.TagFilterEnum
import com.example.receptia.persistence.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoricViewModel : ViewModel() {
    private val recipeList = mutableListOf<Recipe>()

    private val _filterUiState =
        MutableStateFlow<FilterUiState>(FilterUiState.Filters(tag = TagFilterEnum.ALL))
    val filterUiState: StateFlow<FilterUiState> = _filterUiState

    private val _recipesUiState =
        MutableStateFlow<RecipeHistoricUiState>(RecipeHistoricUiState.Loading)
    val recipesUiState: StateFlow<RecipeHistoricUiState> = _recipesUiState

    fun updateRecipeHistoric() {
        viewModelScope.launch {
            _recipesUiState.value = RecipeHistoricUiState.Loading

            val recipes = Recipe.find()
            recipeList.addAll(recipes.toMutableList())

            _recipesUiState.value = RecipeHistoricUiState.Success(recipes = recipes)
        }
    }

    fun updateTagFilter(tagFilter: TagFilterEnum) {
        viewModelScope.launch {
            val state = _filterUiState.value
            if (state is FilterUiState.Filters && state.tag != tagFilter) {
                _filterUiState.value = FilterUiState.Filters(tagFilter)
                _recipesUiState.value = RecipeHistoricUiState.Loading

                when(tagFilter) {
                    TagFilterEnum.ALL -> {
                        _recipesUiState.value = RecipeHistoricUiState.Success(recipeList)
                    }
                    TagFilterEnum.FAVORITES -> {
                        val favorites = recipeList.filter { recipe ->
                            recipe.isFavorite
                        }.toList()

                        _recipesUiState.value = RecipeHistoricUiState.Success(favorites)
                    }
                }
            }
        }
    }
}
