package com.nexusfalcao.recipecatalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.recipecatalog.RecipeCatalogRoute

const val RECIPE_CATALOG_ROUTE = "historic_screen"

fun NavController.navigateToCatalog(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = RECIPE_CATALOG_ROUTE

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

fun NavGraphBuilder.recipeCatalogScreen(
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    navigateToNewRecipe: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    navigateToCatalog: () -> Unit = {},
) {
    composable(RECIPE_CATALOG_ROUTE) {
        RecipeCatalogRoute(
            navigateToAvatar = navigateToAvatar,
            navigateToHome = navigateToHome,
            navigateToNewRecipe = navigateToNewRecipe,
            navigateToRecipeDescription = navigateToRecipeDescription,
            navigateToCatalog = navigateToCatalog,
        )
    }
}
