package com.nexusfalcao.login

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.core.layout.WindowHeightSizeClass
import com.nexusfalcao.authentication.GoogleAuthenticationService
import com.nexusfalcao.authentication.fake.FakeGoogleAuthenticator
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.CustomSnackbar
import com.nexusfalcao.login.state.LoginUiState
import com.nexusfalcao.login.widget.Background
import com.nexusfalcao.login.widget.Description
import com.nexusfalcao.login.widget.GoogleLoginButton
import com.nexusfalcao.login.widget.LoadingButton
import com.nexusfalcao.login.widget.Title
import kotlinx.coroutines.launch

@Composable
internal fun LoginRoute(
    navigateToHome: (Boolean) -> Unit,
    isNetworkConnected: () -> Boolean,
    viewModel: LoginViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    LoginScreen(
        googleAuthenticator = viewModel.googleAuthenticator,
        loginUiState = loginUiState,
        processSignInGoogle = viewModel::processSignInGoogle,
        navigateToHome = navigateToHome,
        startSignInLoading = viewModel::startSignInLoading,
        showSignInError = viewModel::showSignInError,
        isNetworkConnected = isNetworkConnected,
        windowSizeClass = windowSizeClass,
    )
}

@Composable
private fun LoginScreen(
    loginUiState: LoginUiState,
    googleAuthenticator: GoogleAuthenticationService,
    processSignInGoogle: (Intent) -> Unit = {},
    startSignInLoading: () -> Unit = {},
    showSignInError: () -> Unit = {},
    navigateToHome: (Boolean) -> Unit = {},
    isNetworkConnected: () -> Boolean,
    windowSizeClass: WindowSizeClass,
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val snackbarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = stringResource(id = R.string.generic_error_message)
    val networkErrorMessage = stringResource(id = R.string.network_error)

    val bottomPadding = when(windowSizeClass.windowHeightSizeClass) {
        WindowHeightSizeClass.COMPACT -> 50.dp
        WindowHeightSizeClass.MEDIUM -> 50.dp
        else -> 150.dp
    }

    val isLoading =
        when (loginUiState) {
            LoginUiState.Loading -> true
            LoginUiState.Success -> true
            else -> false
        }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                lifecycleScope.launch {
                    val intent = result.data ?: return@launch
                    processSignInGoogle(intent)
                }
            },
        )
    val onClickGoogleSignIn: () -> Unit = {
        lifecycleScope.launch {
            if (!isNetworkConnected()) {
                snackbarHostState.showSnackbar(message = networkErrorMessage)
            } else {
                startSignInLoading()

                val signInIntentSender = googleAuthenticator.initiateGoogleSignIn()
                launcher.launch(
                    if (signInIntentSender != null) {
                        IntentSenderRequest.Builder(
                            signInIntentSender,
                        ).build()
                    } else {
                        showSignInError()
                        return@launch
                    },
                )
            }
        }
    }

    LaunchedEffect(loginUiState) {
        when (loginUiState) {
            is LoginUiState.Error -> {
                snackbarHostState.showSnackbar(message = genericErrorMessage)
            }
            LoginUiState.Success -> {
                val popUp = true
                navigateToHome(popUp)
            }
            else -> {}
        }
    }

    ReceptIaTheme {
        Scaffold(
            snackbarHost = {
                CustomSnackbar.Error(hostState = snackbarHostState)
            },
        ) { padding ->
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
            ) {
                Background()
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Title(windowSizeClass = windowSizeClass)
                    Spacer(modifier = Modifier.height(20.dp))
                    Description(windowSizeClass = windowSizeClass)
                    Spacer(modifier = Modifier.height(50.dp))

                    when (isLoading) {
                        true -> LoadingButton()
                        false -> GoogleLoginButton(
                            loginGoogle = onClickGoogleSignIn,
                            windowSizeClass = windowSizeClass,
                        )
                    }

                    Spacer(modifier = Modifier.height(bottomPadding))
                }
            }
        }
    }
}

@FontSizeAcessibilityPreview
@WindowSizePreview
@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoginPreview() {
    LoginScreen(
        googleAuthenticator = FakeGoogleAuthenticator(),
        loginUiState = LoginUiState.Started,
        isNetworkConnected = { true },
        windowSizeClass = UtilPreview.getPreviewWindowSizeClass()
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoadingPreview() {
    LoginScreen(
        googleAuthenticator = FakeGoogleAuthenticator(),
        loginUiState = LoginUiState.Loading,
        isNetworkConnected = { true },
        windowSizeClass = UtilPreview.getPreviewWindowSizeClass()
    )
}
