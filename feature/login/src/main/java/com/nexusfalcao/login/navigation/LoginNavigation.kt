package com.nexusfalcao.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.login.LoginRoute

const val LOGIN_ROUTE = "login_screen"

fun NavController.navigateToLogin(popUp: Boolean = false) {
    this.navigate(LOGIN_ROUTE) {
        if (popUp) {
            popUpTo(graph.startDestinationId) {
                inclusive = true
            }
        }
    }
}

fun NavGraphBuilder.loginScreen(
    navigateToHome: (Boolean) -> Unit,
    isNetworkConnected: () -> Boolean
) {
    composable(LOGIN_ROUTE) {
        LoginRoute(
            navigateToHome = navigateToHome,
            isNetworkConnected = isNetworkConnected
        )
    }
}
