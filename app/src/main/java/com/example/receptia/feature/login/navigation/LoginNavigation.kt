package com.example.receptia.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.receptia.feature.login.LoginRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToLogin(popUp: Boolean = false) {
    this.navigate(Screen.Login.route) {
        if (popUp) {
            popUpTo(graph.startDestinationId) {
                inclusive = true
            }
        }
    }
}

fun NavGraphBuilder.loginScreen(
    navController: NavController
) {
    composable(Screen.Login.route) {
        LoginRoute(navController = navController)
    }
}
