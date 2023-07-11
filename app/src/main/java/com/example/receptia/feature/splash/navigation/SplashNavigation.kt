package com.example.receptia.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.receptia.feature.splash.SplashRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToSplash() {
    this.navigate(Screen.Splash.route)
}

fun NavGraphBuilder.splashScreen() {
    navigation(
        route = Screen.Splash.route,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            SplashRoute()
        }
    }
}
