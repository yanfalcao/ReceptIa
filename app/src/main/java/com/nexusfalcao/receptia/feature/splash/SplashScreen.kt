package com.nexusfalcao.receptia.feature.splash

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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.feature.home.navigation.navigateToHome
import com.nexusfalcao.receptia.feature.login.navigation.navigateToLogin
import com.nexusfalcao.designsystem.preview.ThemePreviewShowsBakground
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.theme.logoIconResource

@Composable
internal fun SplashRoute(
    navController: NavController,
    viewModel: SplashViewModel = viewModel(),
) {
    val splashUiState by viewModel.splashState.collectAsStateWithLifecycle()

    LaunchedEffect(splashUiState) {
        if (splashUiState is SplashUiState.Success) {
            if ((splashUiState as SplashUiState.Success).logged) {
                navController.navigateToHome(popUp = true)
            } else {
                navController.navigateToLogin(popUp = true)
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
            contentDescription = stringResource(id = R.string.logo_icon_description),
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
