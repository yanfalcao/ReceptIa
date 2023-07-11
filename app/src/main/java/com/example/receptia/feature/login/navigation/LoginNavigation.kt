package com.example.receptia.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.receptia.feature.login.LoginRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToLogin() {
    this.navigate(Screen.Login.route)
}

fun NavGraphBuilder.loginScreen() {
    navigation(
        route = Screen.Login.route,
        startDestination = Screen.Login.route,
    ) {
        composable(Screen.Login.route) {
            LoginRoute()
        }
    }
}