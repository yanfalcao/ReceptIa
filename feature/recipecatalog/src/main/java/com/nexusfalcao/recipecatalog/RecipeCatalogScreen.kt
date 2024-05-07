package com.nexusfalcao.recipecatalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexusfalcao.model.User
import com.nexusfalcao.model.state.RecipeDifficult
import com.nexusfalcao.designsystem.preview.PreviewParameterData as UiPreviewParameterData
import com.nexusfalcao.recipecatalog.preview.PreviewParameterData
import com.nexusfalcao.recipecatalog.state.AmountServesFilterEnum
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum
import com.nexusfalcao.recipecatalog.widget.BottomSheetFilter
import com.nexusfalcao.recipecatalog.widget.FilterButton
import com.nexusfalcao.recipecatalog.widget.GridList
import com.nexusfalcao.recipecatalog.widget.SearchBar
import com.nexusfalcao.recipecatalog.widget.Tag
import com.nexusfalcao.recipecatalog.widget.LoadingRecipeList
import com.nexusfalcao.designsystem.ComposableLifecycle
import com.nexusfalcao.designsystem.preview.ThemePreviewShowsBakground
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.EmptyStateWidget
import com.nexusfalcao.designsystem.widget.NavigationDrawerWidget
import com.nexusfalcao.designsystem.widget.TopBarWidget

@Composable
internal fun RecipeCatalogRoute(
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    navigateToNewRecipe: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    signOut: () -> Unit = {},
    viewModel: CatalogViewModel = hiltViewModel(),
) {
    val historicState by viewModel.recipesUiState.collectAsStateWithLifecycle()
    val filterUiState by viewModel.filterState.collectAsStateWithLifecycle()
    val user = viewModel.getUser()

    CatalogScreen(
        catalogState = historicState,
        filterUiState = filterUiState,
        updateTagFilter = viewModel::updateTagFilter,
        updateSearchFilter = viewModel::updateSearchFilter,
        updateDifficultFilter = viewModel::updateDifficultFilter,
        updateAmountServesFilter = viewModel::updateAmountServesFilter,
        onApplyFilter = viewModel::applyFilter,
        onResetFilter = viewModel::resetFilter,
        user = user,
        navigateToAvatar = navigateToAvatar,
        navigateToHome = navigateToHome,
        navigateToNewRecipe = navigateToNewRecipe,
        navigateToRecipeDescription = navigateToRecipeDescription,
        navigateToCatalog = navigateToCatalog,
        signOut = signOut,
    )

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.updateRecipeHistoric()
            }
            else -> {}
        }
    }
}

@Composable
private fun CatalogScreen(
    catalogState: CatalogUiState,
    filterUiState: FilterState,
    updateTagFilter: (TagFilterEnum) -> Unit = {},
    updateSearchFilter: (String) -> Unit = {},
    updateDifficultFilter: (RecipeDifficult) -> Unit = {},
    updateAmountServesFilter: (AmountServesFilterEnum) -> Unit = {},
    onApplyFilter: () -> Unit = {},
    onResetFilter: () -> Unit = {},
    user: User?,
    navigateToAvatar: () -> Unit = {},
    navigateToHome: () -> Unit = {},
    navigateToNewRecipe: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    navigateToCatalog: () -> Unit = {},
    signOut: () -> Unit = {},
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        BottomSheetFilter(
            filterUiState = filterUiState,
            updateDifficultFilter = updateDifficultFilter,
            updateAmountServesFilter = updateAmountServesFilter,
            onApplyFilter = {
                showSheet = false
                onApplyFilter()
            },
            onResetFilter = {
                showSheet = false
                onResetFilter()
            },
            onDismiss = {
                showSheet = false
                onResetFilter()
            }
        )
    }


    NavigationDrawerWidget(
        drawerState = drawerState,
        toHome = navigateToHome,
        toNewRecipe = navigateToNewRecipe,
        toRecipeCatalog = navigateToCatalog,
        toAvatar = navigateToAvatar,
        onSignOut = signOut,
        userName = user?.name,
        userPhotoId = user?.photoId,
    ) {
        Scaffold(
            topBar = {
                TopBarWidget(
                    title = stringResource(id = R.string.historic_title),
                    drawerState = drawerState,
                )
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(start = 25.dp, end = 25.dp, top = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Row {
                    SearchBar(
                        modifier = Modifier
                            .weight(1.0f)
                            .height(40.dp),
                        updateSearchFilter = updateSearchFilter,
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    FilterButton(
                        modifier = Modifier.size(40.dp),
                        hasAnyFilterSelected = filterUiState.hasAnyFilterSelected(),
                    ) {
                        showSheet = true
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    for (tag in TagFilterEnum.values()) {
                        val tagText = when(tag) {
                            TagFilterEnum.ALL -> stringResource(id = R.string.all)
                            TagFilterEnum.FAVORITES -> stringResource(id = R.string.favorites)
                        }

                        Tag(
                            text = tagText,
                            isSelected = filterUiState.isSelected(tag),
                            updateTagFilter = { updateTagFilter(tag) },
                        )
                    }
                }

                when (catalogState) {
                    CatalogUiState.Loading -> LoadingRecipeList()
                    is CatalogUiState.Success -> {
                        if (catalogState.recipes.isNotEmpty()) {
                            GridList(
                                recipes = catalogState.recipes,
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

}

@ThemePreviewShowsBakground
@Composable
private fun HistoricScreenPreview() {
    ReceptIaTheme {
        CatalogScreen(
            catalogState = CatalogUiState.Success(PreviewParameterData.recipeList),
            filterUiState = FilterState(
                tag = TagFilterEnum.FAVORITES,
                difficult = RecipeDifficult.Easy,
            ),
            navigateToAvatar = {},
            navigateToHome = {},
            navigateToNewRecipe = {},
            navigateToRecipeDescription = {},
            navigateToCatalog = {},
            user = UiPreviewParameterData.user,
        )
    }
}

@ThemePreviewShowsBakground
@Composable
private fun LoadingStatePreview() {
    ReceptIaTheme {
        CatalogScreen(
            catalogState = CatalogUiState.Loading,
            filterUiState = FilterState(TagFilterEnum.ALL),
            navigateToAvatar = {},
            navigateToHome = {},
            navigateToNewRecipe = {},
            navigateToRecipeDescription = {},
            navigateToCatalog = {},
            user = UiPreviewParameterData.user,
        )
    }
}

@ThemePreviewShowsBakground
@Composable
private fun EmptyStatePreview() {
    ReceptIaTheme {
        CatalogScreen(
            catalogState = CatalogUiState.Success(listOf()),
            filterUiState = FilterState(TagFilterEnum.ALL),
            navigateToAvatar = {},
            navigateToHome = {},
            navigateToNewRecipe = {},
            navigateToRecipeDescription = {},
            navigateToCatalog = {},
            user = UiPreviewParameterData.user,
        )
    }
}
