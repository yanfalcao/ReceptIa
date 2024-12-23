package com.nexusfalcao.recipecatalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.User
import com.nexusfalcao.recipecatalog.state.AmountServesFilterEnum
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum
import com.nexusfalcao.model.state.RecipeDifficult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private val recipeList = mutableListOf<Recipe>()

    private val _filterState =
        MutableStateFlow(FilterState(tag = TagFilterEnum.ALL))
    val filterState: StateFlow<FilterState> = _filterState

    private val _recipesUiState =
        MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val recipesUiState: StateFlow<CatalogUiState> = _recipesUiState

    fun getUser(): User? {
        return userRepository.findUser()
    }

    fun updateRecipeHistoric() {
        viewModelScope.launch {
            _recipesUiState.value = CatalogUiState.Loading

            val recipes = recipeRepository.findRecipes()
            recipeList.clear()
            recipeList.addAll(recipes.toMutableList())

            applyFilter()
        }
    }

    fun updateTagFilter(tagFilter: TagFilterEnum) {
        viewModelScope.launch {
            val state = _filterState.value
            if (state.tag != tagFilter) {
                state.tag = tagFilter

                _filterState.value = state
                _recipesUiState.value = CatalogUiState.Loading

                _recipesUiState.value = CatalogUiState.Success(filterList(state))
            }
        }
    }

    fun updateSearchFilter(search: String) {
        viewModelScope.launch {
            val state = _filterState.value
            state.search = search

            _filterState.value = state
            _recipesUiState.value = CatalogUiState.Loading

            _recipesUiState.value = CatalogUiState.Success(filterList(state))
        }
    }

    fun updateDifficultFilter(difficult: RecipeDifficult) {
        viewModelScope.launch {
            val state = _filterState.value
            state.difficult = difficult

            _filterState.value = state.copy()
        }
    }

    fun updateAmountServesFilter(amount: AmountServesFilterEnum) {
        viewModelScope.launch {
            val state = _filterState.value
            state.amountPeopleServes = amount

            _filterState.value = state.copy()
        }
    }

    fun applyFilter() {
        viewModelScope.launch {
            _recipesUiState.value = CatalogUiState.Loading

            val state = _filterState.value
            _recipesUiState.value = CatalogUiState.Success(filterList(state))
        }
    }

    fun resetFilter() {
        viewModelScope.launch {
            val state = _filterState.value
            state.difficult = null
            state.amountPeopleServes = null

            _filterState.value = state
            _recipesUiState.value = CatalogUiState.Loading

            _recipesUiState.value = CatalogUiState.Success(filterList(state))
        }
    }

    private fun filterList(filterState: FilterState): List<Recipe> {
        var filteredList = filterState.filterByTag(recipeList.toList())
        filteredList = filterState.filterBySearch(filteredList)
        filteredList = filterState.filterByDifficult(filteredList)
        filteredList = filterState.filterByAmountServes(filteredList)

        return filteredList
    }
}
