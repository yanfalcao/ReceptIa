package com.nexusfalcao.receptia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nexusfalcao.avatar.navigation.avatarScreen
import com.nexusfalcao.receptia.feature.home.navigation.homeScreen
import com.nexusfalcao.receptia.feature.login.navigation.loginScreen
import com.nexusfalcao.receptia.feature.newRecipe.navigation.newRecipeScreen
import com.nexusfalcao.receptia.feature.recipeDescription.navigation.recipeDescriptionScreen
import com.nexusfalcao.receptia.feature.splash.navigation.splashScreen
import com.nexusfalcao.receptia.navigation.Screen
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.avatar.navigation.navigateToAvatar
import com.nexusfalcao.receptia.feature.home.navigation.navigateToHome
import com.nexusfalcao.receptia.feature.newRecipe.navigation.navigateToNewRecipe
import com.nexusfalcao.receptia.feature.recipeDescription.navigation.navigateToRecipeDescription
import com.nexusfalcao.recipecatalog.navigation.navigateToCatalog
import com.nexusfalcao.recipecatalog.navigation.recipeCatalogScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ReceptIaTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route,
                ) {
                    splashScreen(
                        navController = navController,
                    )
                    loginScreen(
                        navController = navController,
                    )
                    homeScreen(
                        navController = navController,
                    )
                    newRecipeScreen(
                        navController = navController,
                    )
                    recipeDescriptionScreen(
                        navController = navController,
                    )
                    recipeCatalogScreen(
                        navigateToAvatar = navController::navigateToAvatar,
                        navigateToHome = navController::navigateToHome,
                        navigateToNewRecipe = navController::navigateToNewRecipe,
                        navigateToRecipeDescription = navController::navigateToRecipeDescription,
                        navigateToCatalog = navController::navigateToCatalog,
                    )
                    avatarScreen(
                        navController = navController,
                    )
                }
            }
        }
    }
}
