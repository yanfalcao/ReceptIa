package com.nexusfalcao.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexusfalcao.designsystem.preview.ThemePreviewShowsBakground
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.theme.logoIconResource

@Composable
internal fun SplashRoute(
    navigateToHome: (popUp: Boolean) -> Unit,
    navigateToLogin: (popUp: Boolean) -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val splashUiState by viewModel.splashState.collectAsStateWithLifecycle()

    LaunchedEffect(splashUiState) {
        if (splashUiState is SplashUiState.Success) {
            if ((splashUiState as SplashUiState.Success).logged) {
                navigateToHome(true)
            } else {
                navigateToLogin(true)
            }
        }
    }

    SplashScreen()
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = logoIconResource(),
            contentDescription = null,
            Modifier.scale(1.1f),
        )
    }
}

@ThemePreviewShowsBakground
@Composable
fun SplashScreenPreview() {
    ReceptIaTheme {
        SplashScreen()
    }
}
