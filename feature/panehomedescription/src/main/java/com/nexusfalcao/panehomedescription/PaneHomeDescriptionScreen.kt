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
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.description.RecipeDescriptionRoute
import androidx.hilt.navigation.compose.hiltViewModel
import com.nexusfalcao.description.RecipeDescriptionViewModel
import com.nexusfalcao.designsystem.widget.navigationDrawer.CustomNavigationScaffold
import com.nexusfalcao.home.HomeRoute
import com.nexusfalcao.home.HomeViewModel

@Composable
internal fun PaneHomeDescriptionRoute(
    isRequireUpdate: (Context) -> Boolean,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    signOut: () -> Unit = {},
    viewModel: PaneHomeDescriptionVM = hiltViewModel(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val user = viewModel.getUser()

    CustomNavigationScaffold(
        toHome = navigateToHome,
        toNewRecipe = navigateToNewRecipe,
        toRecipeCatalog = navigateToCatalog,
        toAvatar = navigateToAvatar,
        onSignOut = signOut,
        userName = user?.name,
        userPhotoId = user?.photoId,
        windowSizeClass = windowSizeClass,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            PaneHomeDescriptionScreen(
                isRequireUpdate = isRequireUpdate,
                appStoreUrl = appStoreUrl,
                navigateToNewRecipe = navigateToNewRecipe,
                windowSizeClass = windowSizeClass,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun PaneHomeDescriptionScreen(
    isRequireUpdate: (Context) -> Boolean,
    appStoreUrl: String,
    navigateToNewRecipe: () -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    val recipeDescriptionVM = hiltViewModel<RecipeDescriptionViewModel>()
    val homeVM = hiltViewModel<HomeViewModel>()
    val navigator = rememberListDetailPaneScaffoldNavigator<ContentDetailPane>(
        scaffoldDirective = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo()),
        isDestinationHistoryAware = false
    )

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                HomeRoute(
                    isRequireUpdate = isRequireUpdate,
                    appStoreUrl = appStoreUrl,
                    navigateToNewRecipe = navigateToNewRecipe,
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
        },
        detailPane = {
            AnimatedPane {
                navigator.currentDestination?.content?.let {
                    recipeDescriptionVM.getRecipe(it.recipeId)
                    recipeDescriptionVM.setRefreshPaneList(homeVM::updateLastRecipes)

                    RecipeDescriptionRoute(
                        onBackClick = navigator::navigateBack,
                        recipeId = it.recipeId,
                        windowSizeClass = windowSizeClass,
                        viewModel = recipeDescriptionVM,
                    )
                }
            }
        },
    )
}
