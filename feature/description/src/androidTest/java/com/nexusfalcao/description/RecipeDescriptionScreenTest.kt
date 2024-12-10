package com.nexusfalcao.description

import androidx.activity.ComponentActivity
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.nexusfalcao.data.repository.RecipeRepository
import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.model.Ingredient
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class RecipeDescriptionScreenTest {
    val recipe = PreviewParameterData.recipe

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displaysRecipeDetails_whenUiStateIsSuccess() {
        composeTestRule.setContent {
            RecipeDescriptionScreen(
                toogleState = ToogleRecipeState.DetailsSelected,
                recipeUiState = RecipeUiState.Success(recipe),
                windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
            )
        }

        composeTestRule.onNodeWithText(recipe.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(recipe.description).assertIsDisplayed()
        composeTestRule.onNodeWithText(recipe.recipeDetails.preparationTime).assertIsDisplayed()
        composeTestRule.onNodeWithText(recipe.recipeDetails.difficult).assertIsDisplayed()
    }

    @Test
    fun displayToRecipeBody_whenToogleButtonClicked() {
        val recipeButtonText = composeTestRule.activity.getString(R.string.recipe)
        val repository = mockk<RecipeRepository>()
        val viewModel = RecipeDescriptionViewModel(
            recipeRepository = repository,
        )

        every { repository.findRecipe(any()) } returns recipe

        composeTestRule.setContent {
            RecipeDescriptionRoute(
                onBackClick = {},
                recipeId = recipe.id,
                viewModel = viewModel
            )
        }

        composeTestRule.onNodeWithText(recipeButtonText).performClick()

        recipe.ingredients.forEach { ingredient: Ingredient ->
            composeTestRule.onNodeWithText(ingredient.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(ingredient.measure).assertIsDisplayed()
        }
    }
}