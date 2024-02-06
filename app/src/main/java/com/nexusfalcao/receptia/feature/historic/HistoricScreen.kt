package com.nexusfalcao.receptia.feature.historic

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.ui.preview.PreviewParameterData as UiPreviewParameterData
import com.nexusfalcao.receptia.feature.historic.preview.PreviewParameterData
import com.nexusfalcao.receptia.feature.historic.state.AmountServesFilterEnum
import com.nexusfalcao.receptia.feature.historic.state.FilterState
import com.nexusfalcao.receptia.feature.historic.state.RecipeHistoricUiState
import com.nexusfalcao.receptia.feature.historic.state.TagFilterEnum
import com.nexusfalcao.receptia.feature.historic.widget.BottomSheetFilter
import com.nexusfalcao.receptia.feature.historic.widget.FilterButton
import com.nexusfalcao.receptia.feature.historic.widget.GridList
import com.nexusfalcao.receptia.feature.historic.widget.SearchBar
import com.nexusfalcao.receptia.feature.historic.widget.Tag
import com.nexusfalcao.receptia.feature.historic.widget.LoadingRecipeList
import com.nexusfalcao.receptia.feature.recipeDescription.navigation.navigateToRecipeDescription
import com.nexusfalcao.receptia.persistence.User
import com.nexusfalcao.receptia.persistence.utils.DifficultState
import com.nexusfalcao.receptia.ui.ComposableLifecycle
import com.nexusfalcao.receptia.ui.preview.ThemePreviewShowsBakground
import com.nexusfalcao.receptia.ui.theme.Gray100
import com.nexusfalcao.receptia.ui.theme.Olivine
import com.nexusfalcao.receptia.ui.widget.EmptyStateWidget
import com.nexusfalcao.receptia.ui.widget.NavigationDrawerWidget
import com.nexusfalcao.receptia.ui.widget.TopBarWidget

@Composable
internal fun HistoricRoute(
    navController: NavController,
    viewModel: HistoricViewModel = viewModel(),
) {
    val historicState by viewModel.recipesUiState.collectAsStateWithLifecycle()
    val filterUiState by viewModel.filterState.collectAsStateWithLifecycle()
    val user = User.find()

    HistoricScreen(
        historicState = historicState,
        filterUiState = filterUiState,
        navController = navController,
        updateTagFilter = viewModel::updateTagFilter,
        updateSearchFilter = viewModel::updateSearchFilter,
        updateDifficultFilter = viewModel::updateDifficultFilter,
        updateAmountServesFilter = viewModel::updateAmountServesFilter,
        onApplyFilter = viewModel::applyFilter,
        onResetFilter = viewModel::resetFilter,
        user = user,
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
private fun HistoricScreen(
    historicState: RecipeHistoricUiState,
    filterUiState: FilterState,
    navController: NavController,
    updateTagFilter: (TagFilterEnum) -> Unit = {},
    updateSearchFilter: (String) -> Unit = {},
    updateDifficultFilter: (DifficultState) -> Unit = {},
    updateAmountServesFilter: (AmountServesFilterEnum) -> Unit = {},
    onApplyFilter: () -> Unit = {},
    onResetFilter: () -> Unit = {},
    user: User,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var showSheet by remember { mutableStateOf(false) }
    val backgroundFilterButton = when(filterUiState.hasAnyFilterSelected()) {
        true -> Olivine
        false -> Gray100
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
            onDismiss = { showSheet = false }
        )
    }


    NavigationDrawerWidget(
        drawerState = drawerState,
        navController = navController,
        userName = user.name,
        userPhotoId = user.photoId,
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
                        backgrounColor = backgroundFilterButton,
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

                when (historicState) {
                    RecipeHistoricUiState.Loading -> LoadingRecipeList()
                    is RecipeHistoricUiState.Success -> {
                        if (historicState.recipes.isNotEmpty()) {
                            GridList(
                                recipes = historicState.recipes,
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

}

@ThemePreviewShowsBakground
@Composable
private fun HistoricScreenPreview() {
    HistoricScreen(
        historicState = RecipeHistoricUiState.Success(PreviewParameterData.recipeList),
        filterUiState = FilterState(TagFilterEnum.ALL),
        navController = rememberNavController(),
        user = UiPreviewParameterData.user,
    )
}

@ThemePreviewShowsBakground
@Composable
private fun LoadingStatePreview() {
    HistoricScreen(
        historicState = RecipeHistoricUiState.Loading,
        filterUiState = FilterState(TagFilterEnum.ALL),
        navController = rememberNavController(),
        user = UiPreviewParameterData.user,
    )
}

@ThemePreviewShowsBakground
@Composable
private fun EmptyStatePreview() {
    HistoricScreen(
        historicState = RecipeHistoricUiState.Success(listOf()),
        filterUiState = FilterState(TagFilterEnum.ALL),
        navController = rememberNavController(),
        user = UiPreviewParameterData.user,
    )
}
