package com.nexusfalcao.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.splash.SplashRoute

const val SPLASH_ROUTE = "splash_screen"

fun NavGraphBuilder.splashScreen(
    navigateToHome: (Boolean) -> Unit,
    navigateToLogin: (Boolean) -> Unit,
) {
    composable(SPLASH_ROUTE) {
        SplashRoute(
            navigateToHome = navigateToHome,
            navigateToLogin = navigateToLogin,
        )
    }
}
