package com.example.receptia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.receptia.feature.avatar.navigation.avatarScreen
import com.example.receptia.feature.historic.navigation.recipeHistoricScreen
import com.example.receptia.feature.home.navigation.homeScreen
import com.example.receptia.feature.login.navigation.loginScreen
import com.example.receptia.feature.newRecipe.navigation.newRecipeScreen
import com.example.receptia.feature.recipeDescription.navigation.recipeDescriptionScreen
import com.example.receptia.feature.splash.navigation.splashScreen
import com.example.receptia.navigation.Screen
import com.example.receptia.persistence.RealmPersistence
import com.example.receptia.ui.theme.ReceptIaTheme
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
                    startDestination = Screen.Home.route,
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
