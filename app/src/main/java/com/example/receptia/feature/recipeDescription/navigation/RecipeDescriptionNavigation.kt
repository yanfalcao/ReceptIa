package com.example.receptia.feature.recipeDescription.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.receptia.feature.recipeDescription.RecipeDescriptionRoute
import com.example.receptia.navigation.Keys
import com.example.receptia.navigation.Screen

fun NavController.navigateToRecipeDescription(recipeId: String) {
    val destination = currentBackStackEntry?.destination?.route
    val route = Screen.RecipeDescription.route
    val routeWithParam = route.replace(
        oldValue = "{${Keys.RECIPE_ID}}",
        newValue = recipeId,
    )

    if (!destination.isNullOrEmpty() && !route.contains(destination)) {
        this.navigate(routeWithParam)
    }
}

fun NavGraphBuilder.recipeDescriptionScreen(
    navController: NavController,
) {
    composable(Screen.RecipeDescription.route) { backStackEntry ->
        backStackEntry.arguments?.getString(Keys.RECIPE_ID)?.let {
            RecipeDescriptionRoute(
                navController = navController,
                recipeId = it,
            )
        }
    }
}
