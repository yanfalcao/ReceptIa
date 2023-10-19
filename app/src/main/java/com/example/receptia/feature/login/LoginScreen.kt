package com.example.receptia.feature.login

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.R
import com.example.receptia.ReceptIaApplication
import com.example.receptia.feature.home.navigation.navigateToHome
import com.example.receptia.feature.login.state.LoginUiState
import com.example.receptia.feature.login.widget.Background
import com.example.receptia.feature.login.widget.Description
import com.example.receptia.feature.login.widget.GoogleLoginButton
import com.example.receptia.feature.login.widget.LoadingButton
import com.example.receptia.feature.login.widget.Title
import com.example.receptia.model.SignInResult
import com.example.receptia.ui.theme.ReceptIaTheme
import com.example.receptia.ui.widget.CustomSnackbar
import kotlinx.coroutines.launch

@Composable
internal fun LoginRoute(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    LoginScreen(
        loginUiState = loginUiState,
        processSignInGoogle = viewModel::processSignInGoogle,
        navigateToHome = navController::navigateToHome,
        startSignInLoading = viewModel::startSignInLoading
    )
}

@Composable
private fun LoginScreen(
    loginUiState: LoginUiState,
    processSignInGoogle: (SignInResult) -> Unit = {},
    navigateToHome: (Boolean) -> Unit = {},
    startSignInLoading: () -> Unit = {},
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val genericErrorMessage = stringResource(id = R.string.generic_error_message)

    val isLoading = when(loginUiState) {
        LoginUiState.Loading -> true
        LoginUiState.Success -> true
        else -> false
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            lifecycleScope.launch {
                val authUiClient = ReceptIaApplication.instance.googleAuthUiClient
                val signInResult = authUiClient.signInWithIntent(
                    intent = result.data ?: return@launch
                )
                processSignInGoogle(signInResult)
            }
        }
    )
    val onClickGoogleSignIn: () -> Unit = {
        lifecycleScope.launch {
            startSignInLoading()

            val authUiClient = ReceptIaApplication.instance.googleAuthUiClient
            val signInIntentSender = authUiClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    signInIntentSender ?: return@launch
                ).build()
            )
        }
    }

    LaunchedEffect(loginUiState) {
        when(loginUiState) {
            is LoginUiState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(message = genericErrorMessage)
                }
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
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                Background()
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                ) {
                    Title()
                    Spacer(modifier = Modifier.height(20.dp))
                    Description()
                    Spacer(modifier = Modifier.height(50.dp))

                    when (isLoading) {
                        true -> LoadingButton()
                        false -> GoogleLoginButton(onClickGoogleSignIn)
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(loginUiState = LoginUiState.Started)
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoadingStatePreview() {
    LoginScreen(loginUiState = LoginUiState.Loading)
}
