package com.example.receptia.feature.avatar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.receptia.feature.avatar.AvatarRoute
import com.example.receptia.navigation.Screen

fun NavController.navigateToAvatar(popUp: Boolean = false) {
    val destination = currentBackStackEntry?.destination?.route
    val route = Screen.Avatar.route

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
    composable(Screen.Avatar.route) {
        AvatarRoute(navController)
    }
}
