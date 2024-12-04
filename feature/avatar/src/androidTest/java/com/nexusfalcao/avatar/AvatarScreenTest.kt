package com.nexusfalcao.avatar

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.nexusfalcao.avatar.state.ImageUiState
import org.junit.Rule
import org.junit.Test

class AvatarScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun avatarScreen_displays_correct_title() {
        val imageUiState = ImageUiState.Selected(imageId = R.drawable.img_man)

        composeTestRule.setContent {
            AvatarScreen(
                imageUiState = imageUiState,
                selectImage = {},
                saveImage = {},
                onBackClick = {},
            )
        }

        composeTestRule.onNodeWithText("Avatar").assertExists()
    }
}