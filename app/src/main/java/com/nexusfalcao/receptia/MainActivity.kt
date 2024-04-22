package com.nexusfalcao.receptia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nexusfalcao.avatar.navigation.avatarScreen
import com.nexusfalcao.home.navigation.homeScreen
import com.nexusfalcao.receptia.feature.login.navigation.loginScreen
import com.nexusfalcao.description.navigation.recipeDescriptionScreen
import com.nexusfalcao.receptia.feature.splash.navigation.splashScreen
import com.nexusfalcao.receptia.navigation.Screen
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.avatar.navigation.navigateToAvatar
import com.nexusfalcao.createrecipe.navigation.createRecipeScreen
import com.nexusfalcao.home.navigation.navigateToHome
import com.nexusfalcao.receptia.configs.RemoteValues
import com.nexusfalcao.createrecipe.navigation.navigateToCreateRecipe
import com.nexusfalcao.description.navigation.navigateToRecipeDescription
import com.nexusfalcao.receptia.utils.UpdateAppUtil
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
                        isRequireUpdate = UpdateAppUtil::requiredUpdate,
                        appStoreUrl = RemoteValues.VALUE_APP_STORE_URL,
                        navigateToAvatar = navController::navigateToAvatar,
                        navigateToNewRecipe = navController::navigateToCreateRecipe,
                        navigateToCatalog = navController::navigateToCatalog,
                        navigateToRecipeDescription = navController::navigateToRecipeDescription,
                        navigateToHome = navController::navigateToHome
                    )
                    createRecipeScreen(
                        chatGptApiModel = RemoteValues.VALUE_CHATGPT_API_MODEL,
                        isChatGptApiEnabled = RemoteValues.VALUE_CHATGPT_API_ENABLED,
                        onNavigateToRecipeDescription = navController::navigateToRecipeDescription,
                        popBackStack = navController::popBackStack
                    )
                    recipeDescriptionScreen(
                        navController = navController,
                    )
                    recipeCatalogScreen(
                        navigateToAvatar = navController::navigateToAvatar,
                        navigateToHome = navController::navigateToHome,
                        navigateToNewRecipe = navController::navigateToCreateRecipe,
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
