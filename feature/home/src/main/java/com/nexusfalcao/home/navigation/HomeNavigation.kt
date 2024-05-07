package com.nexusfalcao.home.navigation

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.home.HomeRoute

const val HOME_ROUTE = "home_screen"

fun NavController.navigateToHome(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = HOME_ROUTE

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
    isRequireUpdate: (Context) -> Boolean,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    navigateToHome: () -> Unit = {},
    signOut: () -> Unit = {},
) {
    composable(HOME_ROUTE) {
        HomeRoute(
            isRequireUpdate = isRequireUpdate,
            appStoreUrl = appStoreUrl,
            navigateToNewRecipe = navigateToNewRecipe,
            navigateToAvatar = navigateToAvatar,
            navigateToCatalog = navigateToCatalog,
            navigateToRecipeDescription = navigateToRecipeDescription,
            navigateToHome = navigateToHome,
            signOut = signOut,
        )
    }
}
