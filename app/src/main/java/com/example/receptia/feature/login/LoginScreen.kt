package com.example.receptia.feature.login

import LoginViewModelPreviewParameterProvider
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.receptia.R
import com.example.receptia.feature.home.navigation.navigateToHome
import com.example.receptia.ui.theme.Green
import com.example.receptia.ui.theme.ReceptIaTheme

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
internal fun LoginRoute(
    @PreviewParameter(LoginViewModelPreviewParameterProvider::class) viewModel: LoginViewModel = LoginViewModel(),
) {
    LoginScreen(
        loginState = viewModel.loginState,
        loginGoogle = viewModel::loginGoogle,
    )
}

@Composable
private fun LoginScreen(
    loginState: LoginUiState,
    loginGoogle: () -> Unit = {},
) {
    val context = LocalContext.current
    val navController = rememberNavController()

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

            when (loginState) {
                LoginUiState.Loading -> {
                    LoadingButton()
                }
                LoginUiState.Started -> {
                    GoogleLoginButton(loginGoogle)
                }
                is LoginUiState.Success -> {
                    LoadingButton()
                    if (loginState.data) {
                        navController.navigateToHome()
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
private fun Background() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = 0f,
            endY = constraints.maxHeight.toFloat(),
        )

        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = stringResource(id = R.string.bg_login_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush),
        )
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(id = R.string.title_login),
        color = Color.White,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.width(300.dp),
    )
}

@Composable
private fun Description() {
    Text(
        text = stringResource(id = R.string.description_login),
        color = Color.White,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .width(300.dp),
    )
}

@Composable
private fun GoogleLoginButton(loginGoogle: () -> Unit = {}) {
    Button(
        onClick = loginGoogle,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = stringResource(id = R.string.google_icon_description),
                modifier = Modifier
                    .height(32.dp)
                    .width(32.dp)
                    .align(Alignment.CenterVertically),
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = stringResource(id = R.string.google_login),
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
        }
    }
}

@Composable
private fun LoadingButton() {
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 30.dp),
            )
            .height(55.dp)
            .width(55.dp),
    ) {
        CircularProgressIndicator(
            color = Green,
            modifier = Modifier
                .align(Alignment.Center)
                .height(40.dp)
                .width(40.dp),
        )
    }
}
