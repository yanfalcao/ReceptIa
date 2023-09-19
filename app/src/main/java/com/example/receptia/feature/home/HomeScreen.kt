package com.example.receptia.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.receptia.R
import com.example.receptia.feature.home.preview.RecipesPreviewParameterProvider
import com.example.receptia.feature.home.state.RecipeFeedUiState
import com.example.receptia.feature.home.widget.Banner
import com.example.receptia.feature.home.widget.LoadingRecipeList
import com.example.receptia.feature.home.widget.RecipeList
import com.example.receptia.feature.newRecipe.navigation.navigateToNewRecipe
import com.example.receptia.feature.recipeDescription.navigation.navigateToRecipeDescription
import com.example.receptia.persistence.Recipe
import com.example.receptia.ui.ComposableLifecycle
import com.example.receptia.ui.widget.EmptyStateWidget
import com.example.receptia.view.widget.NavigationDrawerWidget
import com.example.receptia.view.widget.TopBarWidget

@Composable
internal fun HomeRoute(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val feedState by viewModel.lastRecipesUiState.collectAsStateWithLifecycle()

    HomeScreen(
        feedState = feedState,
        navigateToNewRecipe = navController::navigateToNewRecipe,
        navController = navController,
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
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawerWidget(
        drawerState = drawerState,
        navController = navController,
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
                    color = Color.Black,
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
                                modifier = Modifier.weight(1.0f).fillMaxSize(),
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
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    HomeScreen(
        navController = rememberNavController(),
        feedState = RecipeFeedUiState.Success(recipes = recipes),
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoadingStatePreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    HomeScreen(
        navController = rememberNavController(),
        feedState = RecipeFeedUiState.Loading,
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun EmptyStatePreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    HomeScreen(
        navController = rememberNavController(),
        feedState = RecipeFeedUiState.Success(recipes = listOf()),
    )
}
