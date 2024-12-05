package com.nexusfalcao.avatar

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.nexusfalcao.avatar.state.ImageUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class AvatarScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun avatarScreen_displaysCorrectTitle() {
        val avatarTitle = composeTestRule.activity.getString(R.string.avatar)
        val imageUiState = ImageUiState.Selected(imageId = R.drawable.img_man)

        composeTestRule.setContent {
            AvatarScreen(
                imageUiState = imageUiState,
                selectImage = {},
                saveImage = {},
                onBackClick = {},
            )
        }

        composeTestRule.onNodeWithText(avatarTitle).assertExists()
    }

    @Test
    fun selectButton_isEnabled_whenImageIsSelected() {
        val selectText = composeTestRule.activity.getString(R.string.select)
        val imageUiState = ImageUiState.Selected(imageId = R.drawable.img_man)

        composeTestRule.setContent {
            AvatarScreen(
                imageUiState = imageUiState,
                selectImage = {},
                saveImage = {},
                onBackClick = {},
            )
        }

        composeTestRule.onNodeWithText(selectText).assertIsEnabled()
    }

    @Test
    fun selectButton_isDisabled_whenNoImageIsSelected() {
        val selectText = composeTestRule.activity.getString(R.string.select)
        val imageUiState = ImageUiState.Unselected

        composeTestRule.setContent {
            AvatarScreen(
                imageUiState = imageUiState,
                selectImage = {},
                saveImage = {},
                onBackClick = {},
            )
        }

        composeTestRule.onNodeWithText(selectText).assertIsNotEnabled()
    }

    @Test
    fun clickingSelectButton_callsSaveImage() {
        val selectText = composeTestRule.activity.getString(R.string.select)

        val viewModel: AvatarViewModel = mockk(relaxed = true)
        val imageUiState = ImageUiState.Selected(imageId = R.drawable.img_man)

        every { viewModel.imageUiState } returns MutableStateFlow(imageUiState)

        composeTestRule.setContent {
            AvatarRoute(
                onBackClick = {},
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithText(selectText).performClick()

        verify{ viewModel.saveImage() }
    }
}