package com.nexusfalcao.home

import android.content.Context
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.User
import com.nexusfalcao.home.preview.RecipesPreviewParameterProvider
import com.nexusfalcao.home.state.RecipeFeedUiState
import com.nexusfalcao.home.widget.Banner
import com.nexusfalcao.home.widget.LoadingRecipeList
import com.nexusfalcao.home.widget.RecipeList
import com.nexusfalcao.designsystem.ComposableLifecycle
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.ThemePreviewShowsBakground
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.CustomUpdateDialog
import com.nexusfalcao.designsystem.widget.EmptyStateWidget
import com.nexusfalcao.designsystem.widget.NavigationDrawerWidget
import com.nexusfalcao.designsystem.widget.TopBarWidget

@Composable
internal fun HomeRoute(
    isRequireUpdate: (Context) -> Boolean,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    navigateToHome: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val feedState by viewModel.lastRecipesUiState.collectAsStateWithLifecycle()
    val user = viewModel.getUser()

    HomeScreen(
        feedState = feedState,
        navigateToNewRecipe = navigateToNewRecipe,
        navigateToAvatar = navigateToAvatar,
        navigateToCatalog = navigateToCatalog,
        navigateToRecipeDescription = navigateToRecipeDescription,
        navigateToHome = navigateToHome,
        isRequireUpdate = isRequireUpdate(context),
        appStoreUrl = appStoreUrl,
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
    feedState: RecipeFeedUiState,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    navigateToHome: () -> Unit = {},
    isRequireUpdate: Boolean,
    user: User?,
) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawerWidget(
        drawerState = drawerState,
        toHome = navigateToHome,
        toNewRecipe = navigateToNewRecipe,
        toRecipeCatalog = navigateToCatalog,
        toAvatar = navigateToAvatar,
        onSignOut = {},
        userName = user?.name,
        userPhotoId = user?.photoId,
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
                                navigateToDescription = navigateToRecipeDescription,
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appStoreUrl))
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
            feedState = RecipeFeedUiState.Success(recipes = recipes),
            isRequireUpdate = false,
            appStoreUrl = "",
            user = PreviewParameterData.user,
        )
    }
}

@ThemePreviewShowsBakground
@Composable
private fun LoadingStatePreview() {
    ReceptIaTheme {
        HomeScreen(
            feedState = RecipeFeedUiState.Loading,
            isRequireUpdate = false,
            appStoreUrl = "",
            user = PreviewParameterData.user,
        )
    }
}

@ThemePreviewShowsBakground
@Composable
private fun EmptyStatePreview() {
    ReceptIaTheme {
        HomeScreen(
            feedState = RecipeFeedUiState.Success(recipes = listOf()),
            isRequireUpdate = false,
            appStoreUrl = "",
            user = PreviewParameterData.user,
        )
    }
}
