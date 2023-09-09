package com.example.receptia.feature.newRecipe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.receptia.feature.newRecipe.NewRecipeRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToNewRecipe() {
    val destination = currentBackStackEntry?.destination?.route
    val route = Screen.NewRecipe.route

    if (!destination.isNullOrEmpty() && !destination.equals(route)) {
        this.navigate(route)
    }
}

fun NavGraphBuilder.newRecipeScreen(
    navController: NavController,
) {
    composable(Screen.NewRecipe.route) {
        NewRecipeRoute(navController)
    }
}
