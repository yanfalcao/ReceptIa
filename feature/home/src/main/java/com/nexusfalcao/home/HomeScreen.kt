package com.nexusfalcao.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.nexusfalcao.designsystem.ComposableLifecycle
import com.nexusfalcao.designsystem.extension.scaleTitleLargeBy
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.UIModeBakgroundPreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.CustomUpdateDialog
import com.nexusfalcao.designsystem.widget.EmptyStateWidget
import com.nexusfalcao.home.preview.RecipesPreviewParameterProvider
import com.nexusfalcao.home.state.RecipeFeedUiState
import com.nexusfalcao.home.widget.Banner
import com.nexusfalcao.home.widget.LoadingRecipeList
import com.nexusfalcao.home.widget.RecipeList
import com.nexusfalcao.model.Recipe

@Composable
fun HomeRoute(
    isRequireUpdate: (Context) -> Boolean,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val context = LocalContext.current
    val feedState by viewModel.lastRecipesUiState.collectAsStateWithLifecycle()

    HomeScreen(
        feedState = feedState,
        navigateToNewRecipe = navigateToNewRecipe,
        navigateToRecipeDescription = navigateToRecipeDescription,
        isRequireUpdate = isRequireUpdate(context),
        appStoreUrl = appStoreUrl,
        windowSizeClass = windowSizeClass,
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
fun HomeScreen(
    feedState: RecipeFeedUiState,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    isRequireUpdate: Boolean,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val context = LocalContext.current
    val fractionWidth = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 1f
        else -> 0.8f
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fractionWidth)
            .padding(horizontal = 25.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Banner(
            windowSizeClass = windowSizeClass,
            navigateToNewRecipe = navigateToNewRecipe
        )

        Text(
            text = stringResource(id = R.string.last_recipes_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = Typography.scaleTitleLargeBy(windowSizeClass),
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
                        windowSizeClass = windowSizeClass,
                    )
                } else {
                    Box(
                        modifier =
                        Modifier
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

    if (isRequireUpdate) {
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

@FontSizeAcessibilityPreview
@UIModeBakgroundPreview
@WindowSizePreview
@Composable
private fun HomePreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    ReceptIaTheme {
        HomeScreen(
            feedState = RecipeFeedUiState.Success(recipes = recipes),
            isRequireUpdate = false,
            appStoreUrl = "",
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}

@UIModeBakgroundPreview
@Composable
private fun LoadingPreview() {
    ReceptIaTheme {
        HomeScreen(
            feedState = RecipeFeedUiState.Loading,
            isRequireUpdate = false,
            appStoreUrl = "",
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}

@FontSizeAcessibilityPreview
@UIModeBakgroundPreview
@Composable
private fun EmptyPreview() {
    ReceptIaTheme {
        HomeScreen(
            feedState = RecipeFeedUiState.Success(recipes = listOf()),
            isRequireUpdate = false,
            appStoreUrl = "",
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}
