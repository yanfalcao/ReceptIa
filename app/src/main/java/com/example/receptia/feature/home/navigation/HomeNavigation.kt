package com.example.receptia.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.receptia.feature.home.HomeRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToHome(popUp: Boolean = false) {
    this.navigate(Screen.Home.route) {
        if (popUp) {
            currentBackStackEntry?.destination?.route?.let { route ->
                popUpTo(route) {
                    inclusive = true
                }
            }
        }
    }
}

fun NavGraphBuilder.homeScreen(
    navController: NavController
) {
    composable(Screen.Home.route) {
        HomeRoute(navController)
    }
}
