package com.nexusfalcao.recipecatalog

import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.RecipeDetails
import com.nexusfalcao.model.Step
import com.nexusfalcao.model.state.RecipeDifficult
import com.nexusfalcao.recipecatalog.state.AmountServesFilterEnum
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import java.util.UUID

class CatalogVMTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var recipe: Recipe

    private var userRepository: UserRepository = mockk(relaxed = true)
    private var recipeRepository: RecipeRepository = mockk(relaxed = true)

    private lateinit var catalogViewModel: CatalogViewModel

    @Before
    fun setUp() {
        recipe = Recipe(
            id = UUID.randomUUID().toString(),
            name = "Espaguete com Molho de Cogumelos e Bacon",
            description = "Filé de frango suculento temperado com limão e acompanhado de  cogumelos salteados.",
            isFavorite = true,
            recipeDetails = RecipeDetails(
                preparationTime = "30 min",
                difficult = "Fácil",
                amountCalories = "450 kcal",
                amountCarbs = "60g",
                amountProteins = "15g",
                amountPeopleServes = 2,
                difficultLevel = 1
            ),
            ingredients = listOf(
                Ingredient(
                    id = UUID.randomUUID().toString(),
                    name = "Filé de Frango",
                    measure = "2 unid"
                ),
            ),
            recipeSteps = listOf(
                Step(
                    id = UUID.randomUUID().toString(),
                    position = 1,
                    description = "Tempere os filés de frango com suco de limão, alho picado, sal e pimenta a gosto."
                ),
            ),
            createdAt = Date()
        )
        catalogViewModel = CatalogViewModel(
            userRepository = userRepository,
            recipeRepository = recipeRepository
        )
    }

    @Test
    fun updatesRecipeHistoric_whenRepositoryReturnsRecipes() = runTest {
        val recipes = listOf(recipe)
        coEvery { recipeRepository.findRecipes() } returns recipes

        catalogViewModel.updateRecipeHistoric()

        assertEquals(CatalogUiState.Success(recipes), catalogViewModel.recipesUiState.value)
    }

    @Test
    fun updatesTagFilter_whenNewTagIsProvided() = runTest {
        val tagFilter = TagFilterEnum.FAVORITES

        coEvery { recipeRepository.findRecipes() } returns listOf(recipe)

        catalogViewModel.updateRecipeHistoric()
        catalogViewModel.updateTagFilter(tagFilter)

        assertEquals(tagFilter, catalogViewModel.filterState.value.tag)

        val successUiState = catalogViewModel.recipesUiState.value as CatalogUiState.Success
        assertEquals(successUiState.recipes.first().name, recipe.name)
    }

    @Test
    fun updatesSearchFilter_whenNewSearchIsProvided() = runTest {
        val list = listOf(
            recipe,
            recipe.copy(id = UUID.randomUUID().toString(), name = "Chocolate Cake")
        )
        val searchQuery = "Cake"

        coEvery { recipeRepository.findRecipes() } returns list

        catalogViewModel.updateRecipeHistoric()
        catalogViewModel.updateSearchFilter(searchQuery)

        assertEquals(searchQuery, catalogViewModel.filterState.value.search)

        val successUiState = catalogViewModel.recipesUiState.value as CatalogUiState.Success
        assertEquals(successUiState.recipes.size, 1)
    }

    @Test
    fun updatesDifficultFilter_whenNewDifficultIsProvided() = runTest {
        val list = listOf(
            recipe,
            recipe.copy(
                id = UUID.randomUUID().toString(),
                recipeDetails = recipe.recipeDetails.copy(difficultLevel = 3)
            )
        )
        val difficult = RecipeDifficult.Hard

        coEvery { recipeRepository.findRecipes() } returns list

        catalogViewModel.updateRecipeHistoric()
        catalogViewModel.updateDifficultFilter(difficult)
        catalogViewModel.applyFilter()

        assertEquals(difficult, catalogViewModel.filterState.value.difficult)

        val successUiState = catalogViewModel.recipesUiState.value as CatalogUiState.Success
        assertEquals(successUiState.recipes.size, 1)
    }

    @Test
    fun updatesAmountServesFilter_whenNewAmountIsProvided() = runTest {
        val list = listOf(
            recipe,
            recipe.copy(
                id = UUID.randomUUID().toString(),
                recipeDetails = recipe.recipeDetails.copy(amountPeopleServes = 5)
            )
        )
        val amount = AmountServesFilterEnum.FOUR_OR_MORE

        coEvery { recipeRepository.findRecipes() } returns list

        catalogViewModel.updateRecipeHistoric()
        catalogViewModel.updateAmountServesFilter(amount)
        catalogViewModel.applyFilter()

        assertEquals(amount, catalogViewModel.filterState.value.amountPeopleServes)

        val successUiState = catalogViewModel.recipesUiState.value as CatalogUiState.Success
        assertEquals(successUiState.recipes.size, 1)
    }

    @Test
    fun resetsFilter_whenResetFilterIsCalled() = runTest {
        catalogViewModel.updateDifficultFilter(RecipeDifficult.Hard)
        catalogViewModel.updateAmountServesFilter(AmountServesFilterEnum.FOUR_OR_MORE)

        catalogViewModel.resetFilter()

        assertEquals(null, catalogViewModel.filterState.value.difficult)
        assertEquals(null, catalogViewModel.filterState.value.amountPeopleServes)
    }
}