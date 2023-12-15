package com.nexusfalcao.receptia.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.receptia.feature.splash.SplashRoute
import com.nexusfalcao.receptia.navigation.Screen

fun NavGraphBuilder.splashScreen(
    navController: NavController,
) {
    composable(Screen.Splash.route) {
        SplashRoute(
            navController = navController,
        )
    }
}
