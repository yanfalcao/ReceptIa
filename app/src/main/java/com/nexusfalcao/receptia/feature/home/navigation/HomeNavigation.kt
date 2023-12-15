package com.nexusfalcao.receptia.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.receptia.feature.home.HomeRoute
import com.nexusfalcao.receptia.navigation.Screen

fun NavController.navigateToHome(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = Screen.Home.route

    if (!destination.isNullOrEmpty() && !destination.equals(route)) {
        this.navigate(route) {
            if (popUp) {
                popUpTo(destination) {
                    inclusive = true
                }
            }
        }
    }
}

fun NavGraphBuilder.homeScreen(
    navController: NavController,
) {
    composable(Screen.Home.route) {
        HomeRoute(navController)
    }
}
