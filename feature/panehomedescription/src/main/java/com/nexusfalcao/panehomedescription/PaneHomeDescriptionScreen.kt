package com.nexusfalcao.panehomedescription

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.window.core.layout.WindowSizeClass
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexusfalcao.description.RecipeDescriptionScreen
import com.nexusfalcao.description.RecipeDescriptionViewModel
import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState
import com.nexusfalcao.designsystem.ComposableLifecycle
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.EmptyPaneWidget
import com.nexusfalcao.designsystem.widget.navigationDrawer.CustomNavigationScaffold
import com.nexusfalcao.home.HomeScreen
import com.nexusfalcao.home.HomeViewModel
import com.nexusfalcao.home.state.RecipeFeedUiState
import com.nexusfalcao.model.Ingredient
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.model.User
import com.nexusfalcao.panehomedescription.state.DetailPaneState
import com.nexusfalcao.panehomedescription.state.ListPaneState
import com.nexusfalcao.panehomedescription.state.NavigationState

@Composable
internal fun PaneHomeDescriptionRoute(
    isRequireUpdate: (Context) -> Boolean,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    signOut: () -> Unit = {},
    recipeDescriptionVM: RecipeDescriptionViewModel = hiltViewModel(),
    homeVM: HomeViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val context = LocalContext.current

    val feedState = homeVM.lastRecipesUiState.collectAsStateWithLifecycle()

    val toogleRecipeState = recipeDescriptionVM.toogleRecipeState.collectAsStateWithLifecycle()
    val recipeUiState = recipeDescriptionVM.recipeUiState.collectAsStateWithLifecycle()

    val user = homeVM.getUser()

    val navigationState = NavigationState(
        navigateToHome = navigateToHome,
        navigateToNewRecipe = navigateToNewRecipe,
        navigateToCatalog = navigateToCatalog,
        navigateToAvatar = navigateToAvatar,
        signOut = signOut,
        user = user,
    )

    val listPaneState = ListPaneState(
        feedState = feedState.value,
        appStoreUrl = appStoreUrl,
        navigateToNewRecipe = navigateToNewRecipe,
        removeRecipe = homeVM::removeRecipe,
        isRequireUpdate = isRequireUpdate(context),
    )

    val detailPaneState = DetailPaneState(
        toogleState = toogleRecipeState.value,
        recipeUiState = recipeUiState.value,
        onToogleFavorite = { recipeId ->
            recipeDescriptionVM.toogleFavorite(recipeId)
            homeVM.updateLastRecipes()
        },
        onSelectToogle = recipeDescriptionVM::selectRecipeToogle,
        refreshPane = recipeDescriptionVM::getRecipe
    )

    PaneHomeDescriptionScreen(
        navigationState = navigationState,
        listPaneState = listPaneState,
        detailPaneState = detailPaneState,
        windowSizeClass = windowSizeClass,
    )

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                homeVM.updateLastRecipes()
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun PaneHomeDescriptionScreen(
    navigationState: NavigationState,
    listPaneState: ListPaneState,
    detailPaneState: DetailPaneState,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<ContentDetailPane>(
        scaffoldDirective = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo()),
    )

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    CustomNavigationScaffold(
        toHome = navigationState.navigateToHome,
        toNewRecipe = navigationState.navigateToNewRecipe,
        toRecipeCatalog = navigationState.navigateToCatalog,
        toAvatar = navigationState.navigateToAvatar,
        onSignOut = navigationState.signOut,
        user = navigationState.user,
        isNavigationEnabled = shouldShowNavigator(navigator),
        windowSizeClass = windowSizeClass,
    ) { padding ->
        ListDetailPaneScaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                HomePane(
                    listPaneState = listPaneState,
                    navigator = navigator,
                    windowSizeClass = windowSizeClass,
                )
            },
            detailPane = {
                RecipeDescriptionPane(
                    navigator = navigator,
                    windowSizeClass = windowSizeClass,
                    detailPaneState = detailPaneState,
                )
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.RecipeDescriptionPane(
    detailPaneState: DetailPaneState,
    navigator: ThreePaneScaffoldNavigator<ContentDetailPane>,
    windowSizeClass: WindowSizeClass,
) {
    val content = navigator.currentDestination?.content

    AnimatedPane {
        if(content != null) {
            navigator.currentDestination?.content?.let {
                detailPaneState.refreshPane(it.recipeId)

                RecipeDescriptionScreen(
                    toogleState = detailPaneState.toogleState,
                    recipeUiState = detailPaneState.recipeUiState,
                    onToogleFavorite = {
                        detailPaneState.onToogleFavorite(it.recipeId)
                    },
                    onSelectToogle = detailPaneState.onSelectToogle,
                    onBackClick = navigator::navigateBack,
                    windowSizeClass = windowSizeClass,
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                EmptyPaneWidget()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.HomePane(
    listPaneState: ListPaneState,
    navigator: ThreePaneScaffoldNavigator<ContentDetailPane>,
    windowSizeClass: WindowSizeClass,
) {
    AnimatedPane {
        HomeScreen(
            feedState = listPaneState.feedState,
            isRequireUpdate = listPaneState.isRequireUpdate,
            appStoreUrl = listPaneState.appStoreUrl,
            navigateToNewRecipe = listPaneState.navigateToNewRecipe,
            navigateToRecipeDescription = { item ->
                navigator.navigateTo(
                    ListDetailPaneScaffoldRole.Detail,
                    ContentDetailPane(item)
                )
            },
            onRemove = listPaneState.removeRecipe,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun shouldShowNavigator(navigator: ThreePaneScaffoldNavigator<ContentDetailPane>): Boolean {
    val isDetailPaneExpanded = navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
    val isMonoPartition = navigator.scaffoldDirective.maxHorizontalPartitions == 1

    return  (isDetailPaneExpanded && isMonoPartition).not()
}

@Composable
@WindowSizePreview
fun PanePreview() {
    ReceptIaTheme {
        PaneHomeDescriptionScreen(
            navigationState = NavigationState(
                user = User(
                    id = "1",
                    name = "Name LastName",
                    isLoggedIn = true
                )
            ),
            listPaneState = ListPaneState(
                feedState = RecipeFeedUiState.Loading,
                appStoreUrl = "",
                navigateToNewRecipe = {},
                isRequireUpdate = false,
            ),
            detailPaneState = DetailPaneState(
                toogleState = ToogleRecipeState.DetailsSelected,
                recipeUiState = RecipeUiState.Loading,
                onToogleFavorite = {},
                onSelectToogle = {},
                refreshPane = {},
            ),
        )
    }
}
