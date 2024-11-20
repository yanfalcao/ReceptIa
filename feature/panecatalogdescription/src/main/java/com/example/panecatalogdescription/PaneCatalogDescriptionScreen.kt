package com.example.panecatalogdescription

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.description.RecipeDescriptionRoute
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexusfalcao.description.RecipeDescriptionViewModel
import com.nexusfalcao.designsystem.widget.navigationDrawer.CustomNavigationScaffold
import com.nexusfalcao.recipecatalog.CatalogViewModel
import com.nexusfalcao.recipecatalog.RecipeCatalogRoute

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
internal fun PaneCatalogDescriptionRoute(
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    signOut: () -> Unit = {},
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val recipeDescriptionVM = hiltViewModel<RecipeDescriptionViewModel>()
    val catalogVM = hiltViewModel<CatalogViewModel>()
    val navigator = rememberListDetailPaneScaffoldNavigator<ContentDetailPane>(
        scaffoldDirective = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo()),
    )

    val user = catalogVM.getUser()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    CustomNavigationScaffold(
        toHome = navigateToHome,
        toNewRecipe = navigateToNewRecipe,
        toRecipeCatalog = navigateToCatalog,
        toAvatar = navigateToAvatar,
        onSignOut = signOut,
        user = user,
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
                    navigator = navigator,
                    windowSizeClass = windowSizeClass,
                    homeVM = catalogVM,
                )
            },
            detailPane = {
                RecipeDescriptionPane(
                    navigator = navigator,
                    windowSizeClass = windowSizeClass,
                    recipeDescriptionVM = recipeDescriptionVM,
                    updatePaneList = catalogVM::updateRecipeHistoric,
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
    recipeDescriptionVM: RecipeDescriptionViewModel,
    updatePaneList: () -> Unit,
) {
    AnimatedPane {
        navigator.currentDestination?.content?.let {
            recipeDescriptionVM.getRecipe(it.recipeId)
            recipeDescriptionVM.setRefreshPaneList(updatePaneList)

            RecipeDescriptionRoute(
                onBackClick = navigator::navigateBack,
                recipeId = it.recipeId,
                windowSizeClass = windowSizeClass,
                viewModel = recipeDescriptionVM,
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.CatalogPane(
    navigator: ThreePaneScaffoldNavigator<ContentDetailPane>,
    windowSizeClass: WindowSizeClass,
    homeVM: CatalogViewModel,
) {
    AnimatedPane {
        RecipeCatalogRoute(
            navigateToRecipeDescription = { item ->
                navigator.navigateTo(
                    ListDetailPaneScaffoldRole.Detail,
                    ContentDetailPane(item)
                )
            },
            windowSizeClass = windowSizeClass,
            viewModel = homeVM,
        )
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun shouldShowNavigator(navigator: ThreePaneScaffoldNavigator<ContentDetailPane>): Boolean {
    val isDetailPaneExpanded = navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
    val isMonoPartition = navigator.scaffoldDirective.maxHorizontalPartitions == 1

    return  (isDetailPaneExpanded && isMonoPartition).not()
}
