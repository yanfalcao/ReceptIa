package com.nexusfalcao.createrecipe

import androidx.activity.ComponentActivity
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.CreateRecipeUiState
import com.nexusfalcao.createrecipe.state.ErrorUiState
import com.nexusfalcao.createrecipe.state.FieldsUiState
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class CreateRecipeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun displaysCorrectTitle() {
        val title = composeTestRule.activity.getString(R.string.new_recipe_title)

        composeTestRule.setContent {
            CreateRecipeScreen(
                fieldsUiState = FieldsUiState(),
                checkFieldUiState = CheckFieldUiState.None,
                createRecipeUiState = CreateRecipeUiState.None,
                errorUiState = ErrorUiState.None,
                createRecipe = {},
                addPreference = { _, _ -> },
                removePreference = { _, _ -> },
                onBackClick = {},
                onNavigateToRecipe = {},
                cleanCreateRecipeUiState = {},
                isChatGptApiEnabled = false,
                isNetworkConnected = { true },
            )
        }

        composeTestRule.onNodeWithText(title).assertIsDisplayed()
    }

    @Test
    fun showsFieldError_whenTriesCreateRecipeWithUnfilledFields() {
        val continueButtonText = composeTestRule.activity.getString(R.string.start)
        val fieldErrorMessage = composeTestRule.activity.getString(R.string.error_field)
        val viewModel = CreateRecipeViewModel(
            recipeRepository = mockk(),
            crashlytics = mockk()
        )

        composeTestRule.setContent {
            CreateRecipeRoute(
                isChatGptApiEnabled = true,
                isNetworkConnected = { true },
                chatGptApiModel = "testApiModel",
                onNavigateToRecipeDescription = {},
                popBackStack = {},
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithText(continueButtonText).performClick()

        composeTestRule.onAllNodesWithText(fieldErrorMessage).assertCountEquals(2)
    }

    @Test
    fun showsErrorSnackbar_whenNetworkIsDisconnected() {
        val continueButtonText = composeTestRule.activity.getString(R.string.start)
        val networkErrorMessage = composeTestRule.activity.getString(R.string.network_error)

        composeTestRule.setContent {
            CreateRecipeScreen(
                fieldsUiState = FieldsUiState(),
                checkFieldUiState = CheckFieldUiState.None,
                createRecipeUiState = CreateRecipeUiState.None,
                errorUiState = ErrorUiState.None,
                createRecipe = {},
                addPreference = { _, _ -> },
                removePreference = { _, _ -> },
                onBackClick = {},
                onNavigateToRecipe = {},
                cleanCreateRecipeUiState = {},
                isChatGptApiEnabled = false,
                isNetworkConnected = { false },
                windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
            )
        }

        composeTestRule.onNodeWithText(continueButtonText).performClick()

        composeTestRule.onNodeWithText(networkErrorMessage).assertIsDisplayed()
    }

    @Test
    fun callsCreateRecipe_whenContinueButtonClicked() {
        val continueButtonText = composeTestRule.activity.getString(R.string.start)
        val createRecipe: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            CreateRecipeScreen(
                fieldsUiState = FieldsUiState(),
                checkFieldUiState = CheckFieldUiState.None,
                createRecipeUiState = CreateRecipeUiState.None,
                errorUiState = ErrorUiState.None,
                createRecipe = createRecipe,
                addPreference = { _, _ -> },
                removePreference = { _, _ -> },
                onBackClick = {},
                onNavigateToRecipe = {},
                cleanCreateRecipeUiState = {},
                isChatGptApiEnabled = true,
                isNetworkConnected = { true },
                windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
            )
        }

        composeTestRule.onNodeWithText(continueButtonText).performClick()

        coEvery { createRecipe() }
    }
}