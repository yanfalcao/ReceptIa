package com.example.receptia.feature.historic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.historic.state.FilterState
import com.example.receptia.feature.historic.state.RecipeHistoricUiState
import com.example.receptia.feature.historic.state.TagFilterEnum
import com.example.receptia.persistence.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HistoricViewModel : ViewModel() {
    private val recipeList = mutableListOf<Recipe>()

    private val _filterState =
        MutableStateFlow(FilterState(tag = TagFilterEnum.ALL))
    val filterState: StateFlow<FilterState> = _filterState

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
            val state = _filterState.value
            if (state.tag != tagFilter) {
                state.tag = tagFilter

                _filterState.value = state
                _recipesUiState.value = RecipeHistoricUiState.Loading

                _recipesUiState.value = RecipeHistoricUiState.Success(filterList(state))
            }
        }
    }

    fun updateSearchFilter(search: String) {
        viewModelScope.launch {
            val state = _filterState.value
            state.search = search

            _filterState.value = state
            _recipesUiState.value = RecipeHistoricUiState.Loading

            _recipesUiState.value = RecipeHistoricUiState.Success(filterList(state))
        }
    }

    private fun filterList(filterState: FilterState): List<Recipe> {
        var filteredList = filterState.filterByTag(recipeList.toList())
        filteredList = filterState.filterBySearch(filteredList)

        return filteredList
    }
}
