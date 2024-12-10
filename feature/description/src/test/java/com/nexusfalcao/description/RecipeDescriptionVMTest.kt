package com.nexusfalcao.description

import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState
import com.nexusfalcao.model.Recipe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecipeDescriptionVMTest {

    private lateinit var viewModel: RecipeDescriptionViewModel
    private val recipeRepository: RecipeRepository = mockk()

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = RecipeDescriptionViewModel(recipeRepository)
    }

    @Test
    fun getRecipe_updatesRecipeUiStateToSuccess_whenRecipeIsFound() = runTest {
        val recipe = mockk<Recipe>()
        coEvery { recipeRepository.findRecipe(any()) } returns recipe

        viewModel.getRecipe("testRecipeId")

        assertEquals(RecipeUiState.Success(recipe), viewModel.recipeUiState.first())
    }

    @Test
    fun getRecipe_doesNotUpdateRecipeUiState_whenRecipeIsNotFound() = runTest {
        coEvery { recipeRepository.findRecipe(any()) } returns null

        viewModel.getRecipe("testRecipeId")

        assertEquals(RecipeUiState.Loading, viewModel.recipeUiState.first())
    }

    @Test
    fun selectRecipeToogle_togglesToogleRecipeState() = runTest {
        viewModel.selectRecipeToogle()
        assertEquals(ToogleRecipeState.RecipeSelected, viewModel.toogleRecipeState.value)

        viewModel.selectRecipeToogle()
        assertEquals(ToogleRecipeState.DetailsSelected, viewModel.toogleRecipeState.value)
    }

    @Test
    fun toogleFavorite_updatesRecipeUiStateAndCallsRefreshPaneList_whenRecipeIsFound() = runTest {
        val recipe = mockk<Recipe>(relaxed = true)

        coEvery { recipeRepository.findRecipe(any()) } returns recipe
        coEvery { recipeRepository.updateIsFavorite(any(), any()) } returns true

        var refreshPaneListCalled = false

        viewModel.setRefreshPaneList { refreshPaneListCalled = true }

        viewModel.toogleFavorite("testRecipeId")

        coVerify { recipeRepository.updateIsFavorite("testRecipeId", recipe.isFavorite) }
        assertEquals(RecipeUiState.Success(recipe), viewModel.recipeUiState.first())
        assert(refreshPaneListCalled)
    }

    @Test
    fun toogleFavorite_doesNotUpdateRecipeUiState_whenRecipeIsNotFound() = runTest {
        coEvery { recipeRepository.findRecipe(any()) } returns null

        viewModel.toogleFavorite("testRecipeId")

        assertEquals(RecipeUiState.Loading, viewModel.recipeUiState.first())
    }
}