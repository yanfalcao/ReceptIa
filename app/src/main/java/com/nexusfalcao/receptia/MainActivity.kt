package com.nexusfalcao.receptia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.panecatalogdescription.navigation.navigateToPaneCatalogDescription
import com.example.panecatalogdescription.navigation.paneCatalogDescriptionScreen
import com.nexusfalcao.authentication.GoogleAuthenticator
import com.nexusfalcao.avatar.navigation.avatarScreen
import com.nexusfalcao.avatar.navigation.navigateToAvatar
import com.nexusfalcao.createrecipe.navigation.createRecipeScreen
import com.nexusfalcao.createrecipe.navigation.navigateToCreateRecipe
import com.nexusfalcao.description.navigation.navigateToRecipeDescription
import com.nexusfalcao.description.navigation.recipeDescriptionScreen
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.login.navigation.loginScreen
import com.nexusfalcao.login.navigation.navigateToLogin
import com.nexusfalcao.panehomedescription.navigation.navigateToPaneHomeDescription
import com.nexusfalcao.panehomedescription.navigation.paneHomeDescriptionScreen
import com.nexusfalcao.receptia.configs.RemoteValues
import com.nexusfalcao.receptia.utils.UpdateAppUtil
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
                        navigateToHome = navController::navigateToPaneHomeDescription,
                        navigateToLogin = navController::navigateToLogin,
                    )
                    loginScreen(
                        navigateToHome = navController::navigateToPaneHomeDescription,
                        isNetworkConnected = ReceptIaApplication.instance::isNetworkConnected,
                    )
                    paneHomeDescriptionScreen(
                        isRequireUpdate = UpdateAppUtil::requiredUpdate,
                        appStoreUrl = RemoteValues.VALUE_APP_STORE_URL,
                        navigateToNewRecipe = navController::navigateToCreateRecipe,
                        navigateToCatalog = navController::navigateToPaneCatalogDescription,
                        navigateToAvatar = navController::navigateToAvatar,
                        navigateToHome = navController::navigateToPaneHomeDescription,
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
                    paneCatalogDescriptionScreen(
                        navigateToAvatar = navController::navigateToAvatar,
                        navigateToHome = navController::navigateToPaneHomeDescription,
                        navigateToNewRecipe = navController::navigateToCreateRecipe,
                        navigateToCatalog = navController::navigateToPaneCatalogDescription,
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
