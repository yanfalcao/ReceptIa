package com.example.receptia.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.receptia.feature.home.HomeRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToHome() {
    this.navigate(Screen.Home.route)
}

fun NavGraphBuilder.homeScreen() {
    navigation(
        route = Screen.Home.route,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeRoute()
        }
    }
}
