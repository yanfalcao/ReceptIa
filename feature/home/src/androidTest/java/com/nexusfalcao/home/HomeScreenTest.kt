package com.nexusfalcao.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.home.state.RecipeFeedUiState
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displaysEmptyState_whenNoRecipesAreAvailable() {
        composeTestRule.setContent {
            HomeScreen(
                feedState = RecipeFeedUiState.Success(recipes = listOf()),
                isRequireUpdate = false,
                appStoreUrl = "testUrl",
            )
        }

        composeTestRule.onNodeWithText("There are not recipes yet!").assertIsDisplayed()
    }

    @Test
    fun displaysUpdateDialog_whenUpdateIsRequired() {
        val updateTitle = composeTestRule.activity.getString(R.string.update_the_app)
        val updateDescription = composeTestRule.activity.getString(R.string.update_the_app_description)

        composeTestRule.setContent {
            HomeScreen(
                feedState = RecipeFeedUiState.Loading,
                isRequireUpdate = true,
                appStoreUrl = "http://example.com",
            )
        }

        composeTestRule.onNodeWithText(updateTitle).assertIsDisplayed()
        composeTestRule.onNodeWithText(updateDescription).assertIsDisplayed()
    }

    @Test
    fun navigatesToRecipeDescription_whenRecipeIsClicked() {
        val recipe = PreviewParameterData.recipe
        var navigatedToDescription = false

        composeTestRule.setContent {
            HomeScreen(
                feedState = RecipeFeedUiState.Success(recipes = listOf(recipe)),
                isRequireUpdate = false,
                appStoreUrl = "urlTest",
                navigateToRecipeDescription = { navigatedToDescription = true },
            )
        }

        composeTestRule.onNodeWithText(recipe.name).performClick()
        assert(navigatedToDescription)
    }

    @Test
    fun navigatesToNewRecipe_whenBannerIsClicked() {
        val buttonBanner = composeTestRule.activity.getString(R.string.start)
        var navigatedToNewRecipe = false

        composeTestRule.setContent {
            HomeScreen(
                feedState = RecipeFeedUiState.Loading,
                isRequireUpdate = false,
                appStoreUrl = "",
                navigateToNewRecipe = { navigatedToNewRecipe = true },
            )
        }

        composeTestRule.onNodeWithText(buttonBanner).performClick()
        assert(navigatedToNewRecipe)
    }
}