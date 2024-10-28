package com.nexusfalcao.createrecipe.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nexusfalcao.createrecipe.CreateRecipeRoute

const val CREATE_RECIPE_ROUTE = "create_recipe_screen"

fun NavController.navigateToCreateRecipe() {
    val destination = currentBackStackEntry?.destination?.route
    val route = CREATE_RECIPE_ROUTE

    if (!destination.isNullOrEmpty() && !destination.equals(route)) {
        this.navigate(route)
    }
}

fun NavGraphBuilder.createRecipeScreen(
    chatGptApiModel: String,
    isChatGptApiEnabled: Boolean,
    onNavigateToRecipeDescription: (String) -> Unit,
    popBackStack: () -> Unit,
    isNetworkConnected: () -> Boolean,
) {
    composable(CREATE_RECIPE_ROUTE) {
        CreateRecipeRoute(
            onNavigateToRecipeDescription = onNavigateToRecipeDescription,
            popBackStack = popBackStack,
            isChatGptApiEnabled = isChatGptApiEnabled,
            chatGptApiModel = chatGptApiModel,
            isNetworkConnected = isNetworkConnected,
        )
    }
}
