package com.nexusfalcao.receptia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.nexusfalcao.receptia.feature.avatar.navigation.avatarScreen
import com.nexusfalcao.receptia.feature.historic.navigation.recipeHistoricScreen
import com.nexusfalcao.receptia.feature.home.navigation.homeScreen
import com.nexusfalcao.receptia.feature.login.navigation.loginScreen
import com.nexusfalcao.receptia.feature.newRecipe.navigation.newRecipeScreen
import com.nexusfalcao.receptia.feature.recipeDescription.navigation.recipeDescriptionScreen
import com.nexusfalcao.receptia.feature.splash.navigation.splashScreen
import com.nexusfalcao.receptia.navigation.Screen
import com.nexusfalcao.receptia.persistence.RealmPersistence
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme
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
                    recipeHistoricScreen(
                        navController = navController,
                    )
                    avatarScreen(
                        navController = navController,
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        RealmPersistence.closeInstance()
        super.onDestroy()
    }
}
