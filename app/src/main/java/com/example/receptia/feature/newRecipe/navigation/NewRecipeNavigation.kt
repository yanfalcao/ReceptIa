package com.example.receptia.feature.newRecipe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.receptia.feature.newRecipe.NewRecipeRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToNewRecipe(popUp: Boolean = false) {
    this.navigate(Screen.NewRecipe.route) {
        if (popUp) {
            currentBackStackEntry?.destination?.route?.let { route ->
                popUpTo(route) {
                    inclusive = true
                }
            }
        }
    }
}

fun NavGraphBuilder.newRecipeScreen(
    navController: NavController
) {
    composable(Screen.NewRecipe.route) {
        NewRecipeRoute(navController)
    }
}
