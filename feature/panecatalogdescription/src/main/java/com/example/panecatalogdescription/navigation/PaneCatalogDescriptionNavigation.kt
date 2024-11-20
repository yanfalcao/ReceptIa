package com.example.panecatalogdescription.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.panecatalogdescription.PaneCatalogDescriptionRoute

const val PANE_CATALOG_DESCRIPTION_ROUTE = "pane_catalog_description_screen"

fun NavController.navigateToPaneCatalogDescription(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = PANE_CATALOG_DESCRIPTION_ROUTE

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

fun NavGraphBuilder.paneCatalogDescriptionScreen(
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    signOut: () -> Unit = {},
) {
    composable(PANE_CATALOG_DESCRIPTION_ROUTE) {
        PaneCatalogDescriptionRoute(
            navigateToNewRecipe = navigateToNewRecipe,
            navigateToCatalog = navigateToCatalog,
            navigateToAvatar = navigateToAvatar,
            navigateToHome = navigateToHome,
            signOut = signOut,
        )
    }
}
