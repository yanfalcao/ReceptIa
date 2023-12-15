package com.nexusfalcao.receptia.feature.historic.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.receptia.feature.historic.HistoricRoute
import com.nexusfalcao.receptia.navigation.Screen

fun NavController.navigateToHistoric(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = Screen.Historic.route

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

fun NavGraphBuilder.recipeHistoricScreen(
    navController: NavController,
) {
    composable(Screen.Historic.route) {
        HistoricRoute(navController = navController)
    }
}
