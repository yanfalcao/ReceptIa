package com.nexusfalcao.receptia.navigation

sealed class Screen(val route: String) {
    object Splash : Screen(route = "splash_screen")
    object Home : Screen(route = "home_screen")
    object NewRecipe : Screen(route = "new_recipe_screen")
    object RecipeDescription : Screen(route = "recipe_description_screen/{${Keys.RECIPE_ID}}")
    object Login : Screen(route = "login_screen")
    object Historic : Screen(route = "historic_screen")
    object Avatar : Screen(route = "avatar_screen")
}
