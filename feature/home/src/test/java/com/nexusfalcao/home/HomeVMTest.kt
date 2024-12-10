package com.nexusfalcao.home

import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.home.state.RecipeFeedUiState
import com.nexusfalcao.model.Recipe
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeVMTest {
    private lateinit var viewModel: HomeViewModel
    private val recipeRepository: RecipeRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(userRepository, recipeRepository)
    }

    @Test
    fun updateLastRecipes_updatesUiStateToSuccess_whenRecipesAreFound() = runTest {
        val recipes = listOf(mockk<Recipe>())
        coEvery { recipeRepository.findRecipes(any()) } returns recipes

        viewModel.updateLastRecipes()

        assertEquals(RecipeFeedUiState.Success(recipes), viewModel.lastRecipesUiState.first())
    }

    @Test
    fun updateLastRecipes_updatesUiStateToLoading_whenNotCalled() = runTest {
        assertEquals(RecipeFeedUiState.Loading, viewModel.lastRecipesUiState.first())
    }
}