package com.nexusfalcao.receptia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nexusfalcao.authentication.GoogleAuthenticator
import com.nexusfalcao.avatar.navigation.avatarScreen
import com.nexusfalcao.avatar.navigation.navigateToAvatar
import com.nexusfalcao.createrecipe.navigation.createRecipeScreen
import com.nexusfalcao.createrecipe.navigation.navigateToCreateRecipe
import com.nexusfalcao.description.navigation.navigateToRecipeDescription
import com.nexusfalcao.description.navigation.recipeDescriptionScreen
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.home.navigation.homeScreen
import com.nexusfalcao.home.navigation.navigateToHome
import com.nexusfalcao.login.navigation.loginScreen
import com.nexusfalcao.login.navigation.navigateToLogin
import com.nexusfalcao.receptia.configs.RemoteValues
import com.nexusfalcao.receptia.utils.UpdateAppUtil
import com.nexusfalcao.recipecatalog.navigation.navigateToCatalog
import com.nexusfalcao.recipecatalog.navigation.recipeCatalogScreen
import com.nexusfalcao.splash.navigation.SPLASH_ROUTE
import com.nexusfalcao.splash.navigation.splashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
            val onSignOut: () -> Unit = {
                lifecycleScope.launch {
                    GoogleAuthenticator(ReceptIaApplication.instance).signOut()

                    navController.navigateToLogin(popUp = true)
                }
            }

            ReceptIaTheme {
                NavHost(
                    navController = navController,
                    startDestination = SPLASH_ROUTE,
                ) {
                    splashScreen(
                        navigateToHome = navController::navigateToHome,
                        navigateToLogin = navController::navigateToLogin,
                    )
                    loginScreen(
                        navigateToHome = navController::navigateToHome,
                        isNetworkConnected = ReceptIaApplication.instance::isNetworkConnected,
                    )
                    homeScreen(
                        isRequireUpdate = UpdateAppUtil::requiredUpdate,
                        appStoreUrl = RemoteValues.VALUE_APP_STORE_URL,
                        navigateToAvatar = navController::navigateToAvatar,
                        navigateToNewRecipe = navController::navigateToCreateRecipe,
                        navigateToCatalog = navController::navigateToCatalog,
                        navigateToRecipeDescription = navController::navigateToRecipeDescription,
                        navigateToHome = navController::navigateToHome,
                        signOut = onSignOut,
                    )
                    createRecipeScreen(
                        chatGptApiModel = RemoteValues.VALUE_CHATGPT_API_MODEL,
                        isChatGptApiEnabled = RemoteValues.VALUE_CHATGPT_API_ENABLED,
                        onNavigateToRecipeDescription = navController::navigateToRecipeDescription,
                        popBackStack = navController::popBackStack,
                        isNetworkConnected = ReceptIaApplication.instance::isNetworkConnected,
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
                        signOut = onSignOut,
                    )
                    avatarScreen(
                        navController = navController,
                    )
                }
            }
        }
    }
}
