package com.example.receptia.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.feature.home.navigation.navigateToHome
import com.example.receptia.feature.login.state.LoginUiState
import com.example.receptia.feature.login.widget.Background
import com.example.receptia.feature.login.widget.Description
import com.example.receptia.feature.login.widget.GoogleLoginButton
import com.example.receptia.feature.login.widget.LoadingButton
import com.example.receptia.feature.login.widget.Title
import com.example.receptia.ui.theme.ReceptIaTheme

@Composable
internal fun LoginRoute(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
) {
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    LoginScreen(
        loginUiState = loginUiState,
        loginGoogle = viewModel::loginGoogle,
        navigateToHome = navController::navigateToHome,
    )
}

@Composable
private fun LoginScreen(
    loginUiState: LoginUiState,
    loginGoogle: () -> Unit = {},
    navigateToHome: (Boolean) -> Unit = {},
) {
    val isLoading = loginUiState !is LoginUiState.Started

    LaunchedEffect(loginUiState) {
        if (loginUiState is LoginUiState.Success) {
            if (loginUiState.data) {
                val popUp = true
                navigateToHome(popUp)
            }
        }
    }

    ReceptIaTheme {
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
                false -> GoogleLoginButton(loginGoogle)
            }

            Spacer(modifier = Modifier.height(50.dp))
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
