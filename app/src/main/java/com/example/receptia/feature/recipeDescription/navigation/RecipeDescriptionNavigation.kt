package com.example.receptia.feature.recipeDescription.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.receptia.feature.newRecipe.NewRecipeRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToRecipeDescription(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = Screen.RecipeDescription.route

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

fun NavGraphBuilder.recipeDescriptionScreen(
    navController: NavController
) {
    composable(Screen.RecipeDescription.route) {
        NewRecipeRoute(navController)
    }
}
