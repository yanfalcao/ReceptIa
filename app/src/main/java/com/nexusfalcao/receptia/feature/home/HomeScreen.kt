package com.nexusfalcao.receptia.feature.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.configs.RemoteValues
import com.nexusfalcao.receptia.feature.home.preview.RecipesPreviewParameterProvider
import com.nexusfalcao.receptia.feature.home.state.RecipeFeedUiState
import com.nexusfalcao.receptia.feature.home.widget.Banner
import com.nexusfalcao.receptia.feature.home.widget.LoadingRecipeList
import com.nexusfalcao.receptia.feature.home.widget.RecipeList
import com.nexusfalcao.receptia.feature.newRecipe.navigation.navigateToNewRecipe
import com.nexusfalcao.receptia.feature.recipeDescription.navigation.navigateToRecipeDescription
import com.nexusfalcao.receptia.persistence.Recipe
import com.nexusfalcao.receptia.persistence.User
import com.nexusfalcao.receptia.ui.ComposableLifecycle
import com.nexusfalcao.receptia.ui.preview.PreviewParameterData
import com.nexusfalcao.receptia.ui.preview.ThemePreviewShowsBakground
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme
import com.nexusfalcao.receptia.ui.widget.CustomUpdateDialog
import com.nexusfalcao.receptia.ui.widget.EmptyStateWidget
import com.nexusfalcao.receptia.ui.widget.NavigationDrawerWidget
import com.nexusfalcao.receptia.utils.UpdateAppUtil
import com.nexusfalcao.receptia.ui.widget.TopBarWidget

@Composable
internal fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val context = LocalContext.current
    val feedState by viewModel.lastRecipesUiState.collectAsStateWithLifecycle()
    val user = User.find()

    HomeScreen(
        feedState = feedState,
        navigateToNewRecipe = navController::navigateToNewRecipe,
        navController = navController,
        isRequireUpdate = UpdateAppUtil.requiredUpdate(context),
        user = user,
    )

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.updateLastRecipes()
            }
            else -> {}
        }
    }
}

@Composable
private fun HomeScreen(
    navController: NavController,
    feedState: RecipeFeedUiState,
    navigateToNewRecipe: () -> Unit = {},
    isRequireUpdate: Boolean,
    user: User,
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawerWidget(
        drawerState = drawerState,
        navController = navController,
        userName = user.name,
        userPhotoId = user.photoId,
    ) {
        Scaffold(
            topBar = { TopBarWidget(drawerState) },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 25.dp),
            ) {
                Banner(navigateToNewRecipe)

                Text(
                    text = stringResource(id = R.string.last_recipes_title),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(25.dp))

                when (feedState) {
                    RecipeFeedUiState.Loading ->
                        LoadingRecipeList()
                    is RecipeFeedUiState.Success -> {
                        if (feedState.recipes.isNotEmpty()) {
                            RecipeList(
                                feedState.recipes,
                                navigateToDescription = navController::navigateToRecipeDescription,
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .weight(1.0f)
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                EmptyStateWidget()
                            }
                        }
                    }
                }
            }
        }
    }

    if(isRequireUpdate) {
        CustomUpdateDialog(
            title = stringResource(id = R.string.update_the_app),
            description = stringResource(id = R.string.update_the_app_description),
        ) {
            val updateUrl = RemoteValues.VALUE_APP_STORE_URL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}

@ThemePreviewShowsBakground
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    ReceptIaTheme {
        HomeScreen(
            navController = rememberNavController(),
            feedState = RecipeFeedUiState.Success(recipes = recipes),
            isRequireUpdate = false,
            user = PreviewParameterData.user,
        )
    }
}

@ThemePreviewShowsBakground
@Composable
private fun LoadingStatePreview() {
    ReceptIaTheme {
        HomeScreen(
            navController = rememberNavController(),
            feedState = RecipeFeedUiState.Loading,
            isRequireUpdate = false,
            user = PreviewParameterData.user,
        )
    }
}

@ThemePreviewShowsBakground
@Composable
private fun EmptyStatePreview() {
    ReceptIaTheme {
        HomeScreen(
            navController = rememberNavController(),
            feedState = RecipeFeedUiState.Success(recipes = listOf()),
            isRequireUpdate = false,
            user = PreviewParameterData.user,
        )
    }
}
