package com.nexusfalcao.description.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nexusfalcao.description.RecipeDescriptionRoute

const val RECIPE_ID_KEY = "recipe_id"
const val RECIPE_DESCRIPTION_ROUTE = "recipe_description_screen/{${RECIPE_ID_KEY}}"

fun NavController.navigateToRecipeDescription(recipeId: String) {
    val destination = currentBackStackEntry?.destination?.route
    val route = RECIPE_DESCRIPTION_ROUTE
    val routeWithParam = route.replace(
        oldValue = "{${RECIPE_ID_KEY}}",
        newValue = recipeId,
    )

    if (!destination.isNullOrEmpty() && !route.contains(destination)) {
        this.navigate(route = routeWithParam)
    }
}

fun NavGraphBuilder.recipeDescriptionScreen(
    navController: NavController,
) {
    composable(
        RECIPE_DESCRIPTION_ROUTE,
        arguments = listOf(
            navArgument(RECIPE_ID_KEY) {
                type = NavType.StringType
            },
        ),
    ) {
        it.arguments?.getString(RECIPE_ID_KEY)?.let { id ->
            RecipeDescriptionRoute(
                navController = navController,
                recipeId = id,
            )
        }
    }
}
