package com.nexusfalcao.createrecipe

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.CreateRecipeUiState
import com.nexusfalcao.createrecipe.state.ErrorUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.model.Recipe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class CreateRecipeVMTest {
    private lateinit var viewModel: CreateRecipeViewModel
    private val recipeRepository: RecipeRepository = mockk(relaxed = true)
    private val crashlytics: FirebaseCrashlytics = mockk(relaxed = true)

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = CreateRecipeViewModel(recipeRepository, crashlytics)
    }

    @Test
    fun checkFieldUiState_returnsFilled_whenAllFieldsAreFilled() = runTest {
        viewModel.addPreference(RecipeFieldState.FAVORITE, "egg")
        viewModel.addPreference(RecipeFieldState.MEAL, "breakfast")

        val checkFieldUiState = viewModel.checkFieldUiState.first()

        assert(checkFieldUiState is CheckFieldUiState.Filled)
    }

    @Test
    fun checkFieldUiState_returnsError_whenMealIsNotSelected() = runTest {
        viewModel.addPreference(RecipeFieldState.FAVORITE, "egg")
        viewModel.createRecipe("testModel")

        val checkFieldUiState = viewModel.checkFieldUiState.first()

        assert(checkFieldUiState is CheckFieldUiState.Unfilled)
    }

    @Test
    fun createRecipe_setsCreateRecipeUiStateToSuccess_whenRecipeIsCreatedSuccessfully() = runTest {
        val chatGptApiModel = "testModel"
        val recipe = mockk<Recipe>()

        every { recipe.id } returns UUID.randomUUID().toString()
        coEvery { recipeRepository.callNewRecipe(any(), any()) } returns listOf(recipe)
        every { recipeRepository.insertRecipe(any()) } returns true

        viewModel.addPreference(RecipeFieldState.FAVORITE, "egg")
        viewModel.addPreference(RecipeFieldState.MEAL, "breakfast")

        viewModel.checkFieldUiState.first()

        viewModel.createRecipe(chatGptApiModel)

        assert(viewModel.createRecipeUiState.value is CreateRecipeUiState.Success)
    }

    @Test
    fun createRecipe_setsCreateRecipeUiStateToError_whenExceptionIsThrown() = runTest {
        val chatGptApiModel = "testModel"
        coEvery { recipeRepository.callNewRecipe(any(), any()) } throws Exception()

        viewModel.addPreference(RecipeFieldState.FAVORITE, "egg")
        viewModel.addPreference(RecipeFieldState.MEAL, "breakfast")

        viewModel.checkFieldUiState.first()

        viewModel.createRecipe(chatGptApiModel)

        assert(viewModel.createRecipeUiState.value is CreateRecipeUiState.Error)
    }

    @Test
    fun addPreference_addsField_whenCharLimitNotReached() = runTest {
        val text = "newPreference"

        viewModel.addPreference(RecipeFieldState.FAVORITE, text)

        assert(viewModel.errorUiState.value is ErrorUiState.None)
    }

    @Test
    fun addPreference_doesNotAddField_whenCharLimitReached() = runTest {
        val longText = "a".repeat(800)

        viewModel.addPreference(RecipeFieldState.FAVORITE, longText)

        assert(viewModel.errorUiState.value is ErrorUiState.IngredientMaxLimit)
    }

    @Test
    fun removePreference_removesField() = runTest {
        val text = "preferenceToRemove"
        viewModel.addPreference(RecipeFieldState.FAVORITE, text)

        viewModel.removePreference(RecipeFieldState.FAVORITE, text)

        assert(viewModel.fieldsUiState.value.favoriteIngredients.isEmpty())
    }
}