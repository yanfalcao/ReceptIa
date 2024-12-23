package com.nexusfalcao.recipecatalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.nexusfalcao.designsystem.ComposableLifecycle
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.UIModeBakgroundPreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.EmptyStateWidget
import com.nexusfalcao.model.state.RecipeDifficult
import com.nexusfalcao.recipecatalog.preview.PreviewParameterData
import com.nexusfalcao.recipecatalog.state.AmountServesFilterEnum
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum
import com.nexusfalcao.recipecatalog.widget.BottomSheetFilter
import com.nexusfalcao.recipecatalog.widget.FilterButton
import com.nexusfalcao.recipecatalog.widget.GridList
import com.nexusfalcao.recipecatalog.widget.LoadingRecipeList
import com.nexusfalcao.recipecatalog.widget.SearchBar
import com.nexusfalcao.recipecatalog.widget.Tag

@Composable
fun RecipeCatalogRoute(
    navigateToRecipeDescription: (String) -> Unit = {},
    viewModel: CatalogViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
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
        navigateToRecipeDescription = navigateToRecipeDescription,
        windowSizeClass = windowSizeClass,
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
fun CatalogScreen(
    catalogState: CatalogUiState,
    filterUiState: FilterState,
    updateTagFilter: (TagFilterEnum) -> Unit = {},
    updateSearchFilter: (String) -> Unit = {},
    updateDifficultFilter: (RecipeDifficult) -> Unit = {},
    updateAmountServesFilter: (AmountServesFilterEnum) -> Unit = {},
    onApplyFilter: () -> Unit = {},
    onResetFilter: () -> Unit = {},
    navigateToRecipeDescription: (String) -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    var showSheet by remember { mutableStateOf(false) }
    val searchHeight: Dp
    val filterButtonSize: Dp
    val faction = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 1.0f
        else -> 0.8f
    }

    if (windowSizeClass.hasCompactSize()) {
        searchHeight = 40.dp
        filterButtonSize = 40.dp
    } else if (windowSizeClass.hasMediumSize()) {
        searchHeight = 50.dp
        filterButtonSize = 50.dp
    } else {
        searchHeight = 50.dp
        filterButtonSize = 50.dp
    }

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
            },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(fraction = faction)
            .padding(start = 25.dp, end = 25.dp, top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row {
            SearchBar(
                modifier =
                Modifier
                    .weight(1.0f)
                    .height(searchHeight),
                filterUiState = filterUiState,
                updateSearchFilter = updateSearchFilter,
            )

            Spacer(modifier = Modifier.width(15.dp))

            FilterButton(
                modifier = Modifier.size(filterButtonSize),
                hasAnyFilterSelected = filterUiState.hasAnyFilterSelected(),
            ) {
                showSheet = true
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            for (tag in TagFilterEnum.entries) {
                val tagText =
                    when (tag) {
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
}

@FontSizeAcessibilityPreview
@UIModeBakgroundPreview
@WindowSizePreview
@Composable
private fun HistoricPreview() {
    ReceptIaTheme {
        CatalogScreen(
            catalogState = CatalogUiState.Success(PreviewParameterData.recipeList),
            filterUiState =
                FilterState(
                    tag = TagFilterEnum.FAVORITES,
                    difficult = RecipeDifficult.Easy,
                ),
            navigateToRecipeDescription = {},
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}

@UIModeBakgroundPreview
@Composable
private fun LoadingPreview() {
    ReceptIaTheme {
        CatalogScreen(
            catalogState = CatalogUiState.Loading,
            filterUiState = FilterState(TagFilterEnum.ALL),
            navigateToRecipeDescription = {},
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}

@UIModeBakgroundPreview
@Composable
private fun EmptyPreview() {
    ReceptIaTheme {
        CatalogScreen(
            catalogState = CatalogUiState.Success(listOf()),
            filterUiState = FilterState(TagFilterEnum.ALL),
            navigateToRecipeDescription = {},
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}
