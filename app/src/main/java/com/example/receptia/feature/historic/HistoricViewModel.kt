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
            val state = _filterUiState.value as FilterUiState.Filters
            if (state.tag != tagFilter) {
                state.tag = tagFilter

                _filterUiState.value = state
                _recipesUiState.value = RecipeHistoricUiState.Loading

                _recipesUiState.value = RecipeHistoricUiState.Success(filterList(state))
            }
        }
    }

    fun updateSearchFilter(search: String?) {
        viewModelScope.launch {
            val state = _filterUiState.value as FilterUiState.Filters
            state.search = search

            _filterUiState.value = state
            _recipesUiState.value = RecipeHistoricUiState.Loading

            _recipesUiState.value = RecipeHistoricUiState.Success(filterList(state))
        }
    }

    private fun filterList(filterState: FilterUiState.Filters): List<Recipe> {
        var filteredList = when (filterState.tag) {
            TagFilterEnum.FAVORITES -> {
                recipeList.filter { recipe ->
                    filterState.tag == TagFilterEnum.FAVORITES && recipe.isFavorite
                }.toList()
            }

            TagFilterEnum.ALL -> recipeList.toList()
        }

        val search = filterState.search
        if (!search.isNullOrEmpty() && search.isNotBlank()) {
            filteredList = filteredList.filter { recipe ->
                recipe.name.lowercase().contains(search.lowercase())
            }.toList()
        }

        return filteredList
    }
}
