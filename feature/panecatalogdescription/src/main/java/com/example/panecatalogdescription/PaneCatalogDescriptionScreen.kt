package com.example.panecatalogdescription

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.panecatalogdescription.state.DetailPaneState
import com.example.panecatalogdescription.state.ListPaneState
import com.example.panecatalogdescription.state.NavigationState
import com.nexusfalcao.description.RecipeDescriptionScreen
import com.nexusfalcao.description.RecipeDescriptionViewModel
import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState
import com.nexusfalcao.designsystem.ComposableLifecycle
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.EmptyPaneWidget
import com.nexusfalcao.designsystem.widget.navigationDrawer.CustomNavigationScaffold
import com.nexusfalcao.recipecatalog.CatalogScreen
import com.nexusfalcao.recipecatalog.CatalogViewModel
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum

@Composable
fun PaneCatalogDescriptionRoute(
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    signOut: () -> Unit = {},
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    recipeDescriptionVM: RecipeDescriptionViewModel = hiltViewModel(),
    catalogVM: CatalogViewModel= hiltViewModel()
) {
    val toogleRecipeState by recipeDescriptionVM.toogleRecipeState.collectAsStateWithLifecycle()
    val recipeUiState by recipeDescriptionVM.recipeUiState.collectAsStateWithLifecycle()
    val historicState by catalogVM.recipesUiState.collectAsStateWithLifecycle()
    val filterUiState by catalogVM.filterState.collectAsStateWithLifecycle()

    val user = catalogVM.getUser()

    val listPaneState = ListPaneState(
        catalogUiState = historicState,
        filterState = filterUiState,
        updateTagFilter = catalogVM::updateTagFilter,
        updateSearchFilter = catalogVM::updateSearchFilter,
        updateDifficultFilter = catalogVM::updateDifficultFilter,
        updateAmountServesFilter = catalogVM::updateAmountServesFilter,
        onApplyFilter = catalogVM::applyFilter,
        onResetFilter = catalogVM::resetFilter,
    )
    val detailPaneState = DetailPaneState(
        toogleState = toogleRecipeState,
        recipeUiState = recipeUiState,
        onToogleFavorite = { recipeId ->
            recipeDescriptionVM.toogleFavorite(recipeId)
            catalogVM.updateRecipeHistoric()
        },
        onSelectToogle = recipeDescriptionVM::selectRecipeToogle,
        refreshPane = recipeDescriptionVM::getRecipe
    )
    val navigationState = NavigationState(
        navigateToNewRecipe = navigateToNewRecipe,
        navigateToCatalog = navigateToCatalog,
        navigateToAvatar = navigateToAvatar,
        navigateToHome = navigateToHome,
        refreshPane = recipeDescriptionVM::getRecipe,
        signOut = signOut,
        user = user,
    )

    PaneCatalogDescriptionScreen(
        listPaneState = listPaneState,
        detailPaneState = detailPaneState,
        navigationState = navigationState,
        windowSizeClass = windowSizeClass,
    )

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                catalogVM.updateRecipeHistoric()
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun PaneCatalogDescriptionScreen(
    listPaneState: ListPaneState,
    detailPaneState: DetailPaneState,
    navigationState: NavigationState,
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
                CatalogPane(
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
    navigator: ThreePaneScaffoldNavigator<ContentDetailPane>,
    windowSizeClass: WindowSizeClass,
    detailPaneState: DetailPaneState,
) {
    val content = navigator.currentDestination?.content
    AnimatedPane {
        if(content != null) {
            detailPaneState.refreshPane(content.recipeId)

            RecipeDescriptionScreen(
                toogleState = detailPaneState.toogleState,
                recipeUiState = detailPaneState.recipeUiState,
                onToogleFavorite = {
                    detailPaneState.onToogleFavorite(content.recipeId)
                },
                onSelectToogle = detailPaneState.onSelectToogle,
                onBackClick = navigator::navigateBack,
                windowSizeClass = windowSizeClass,
            )
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
private fun ThreePaneScaffoldScope.CatalogPane(
    navigator: ThreePaneScaffoldNavigator<ContentDetailPane>,
    windowSizeClass: WindowSizeClass,
    listPaneState: ListPaneState,
) {
    AnimatedPane {
        CatalogScreen(
            catalogState = listPaneState.catalogUiState,
            filterUiState = listPaneState.filterState,
            updateTagFilter = listPaneState.updateTagFilter,
            updateSearchFilter = listPaneState.updateSearchFilter,
            updateDifficultFilter = listPaneState.updateDifficultFilter,
            updateAmountServesFilter = listPaneState.updateAmountServesFilter,
            onApplyFilter = listPaneState.onApplyFilter,
            onResetFilter = listPaneState.onResetFilter,
            navigateToRecipeDescription = { item ->
                navigator.navigateTo(
                    ListDetailPaneScaffoldRole.Detail,
                    ContentDetailPane(item)
                )
            },
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
fun PanePreview(
) {
    ReceptIaTheme {
        PaneCatalogDescriptionScreen(
            detailPaneState = DetailPaneState(
                toogleState = ToogleRecipeState.DetailsSelected,
                recipeUiState = RecipeUiState.Loading,
                refreshPane = {recipeId ->},
            ),
            listPaneState = ListPaneState(
                catalogUiState = CatalogUiState.Loading,
                filterState = FilterState(
                    search = "",
                    tag = TagFilterEnum.ALL,
                ),
            ),
            navigationState = NavigationState(
                user = null,
                refreshPane = {recipeId ->},
            ),
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }

}
