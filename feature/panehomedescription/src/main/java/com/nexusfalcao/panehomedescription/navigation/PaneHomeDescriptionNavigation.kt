package com.nexusfalcao.panehomedescription.navigation

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.panehomedescription.PaneHomeDescriptionRoute

const val PANE_HOME_DESCRIPTION_ROUTE = "pane_home_description_screen"

fun NavController.navigateToPaneHomeDescription(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = PANE_HOME_DESCRIPTION_ROUTE

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

fun NavGraphBuilder.paneHomeDescriptionScreen(
    isRequireUpdate: (Context) -> Boolean,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    signOut: () -> Unit = {},
) {
    composable(PANE_HOME_DESCRIPTION_ROUTE) {
        PaneHomeDescriptionRoute(
            isRequireUpdate = isRequireUpdate,
            appStoreUrl = appStoreUrl,
            navigateToNewRecipe = navigateToNewRecipe,
            navigateToCatalog = navigateToCatalog,
            navigateToAvatar = navigateToAvatar,
            navigateToHome = navigateToHome,
            signOut = signOut,
        )
    }
}