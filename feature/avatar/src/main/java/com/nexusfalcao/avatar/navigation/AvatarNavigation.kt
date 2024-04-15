package com.nexusfalcao.avatar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val AVATAR_ROUTE = "avatar_screen"

fun NavController.navigateToAvatar(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = AVATAR_ROUTE

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

fun NavGraphBuilder.avatarScreen(
    navController: NavController,
) {
    composable(AVATAR_ROUTE) {
        com.nexusfalcao.avatar.AvatarRoute(navController)
    }
}
