package com.nexusfalcao.login

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.nexusfalcao.login.state.LoginUiState
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun showsNetworkErrorSnackbar_whenNetworkIsDisconnected() {
        val signText = composeTestRule.activity.getString(R.string.google_login)
        val networkError = composeTestRule.activity.getString(R.string.network_error)

        composeTestRule.setContent {
            LoginScreen(
                loginUiState = LoginUiState.Started,
                googleAuthenticator = mockk(),
                isNetworkConnected = { false },
            )
        }

        composeTestRule.onNodeWithText(signText).performClick()
        composeTestRule.onNodeWithText(networkError).assertIsDisplayed()
    }

    @Test
    fun showsGenericErrorSnackbar_whenLoginUiStateIsError() {
        val genericError = composeTestRule.activity.getString(R.string.generic_error_message)

        composeTestRule.setContent {
            LoginScreen(
                loginUiState = LoginUiState.Error,
                googleAuthenticator = mockk(),
                isNetworkConnected = { true },
            )
        }

        composeTestRule.onNodeWithText(genericError).assertIsDisplayed()
    }

    @Test
    fun navigatesToHome_whenLoginUiStateIsSuccess() {
        var navigatedToHome = false

        composeTestRule.setContent {
            LoginScreen(
                loginUiState = LoginUiState.Success,
                googleAuthenticator = mockk(),
                navigateToHome = { navigatedToHome = true },
                isNetworkConnected = { true },
            )
        }

        assert(navigatedToHome)
    }

    @Test
    fun showsGoogleLoginButton_whenLoginUiStateIsStarted() {
        val googleSignInButton = composeTestRule.activity.getString(R.string.google_login)

        composeTestRule.setContent {
            LoginScreen(
                loginUiState = LoginUiState.Started,
                googleAuthenticator = mockk(),
                isNetworkConnected = { true },
            )
        }

        composeTestRule.onNodeWithText(googleSignInButton).assertIsDisplayed()
    }
}