package com.nexusfalcao.description

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.window.core.layout.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nexusfalcao.description.state.RecipeUiState
import com.nexusfalcao.description.state.ToogleRecipeState
import com.nexusfalcao.description.widget.BackButton
import com.nexusfalcao.description.widget.Background
import com.nexusfalcao.description.widget.DetailsBody
import com.nexusfalcao.description.widget.Header
import com.nexusfalcao.description.widget.RecipeBody
import com.nexusfalcao.description.widget.ToogleButton
import com.nexusfalcao.designsystem.ComposableLifecycle
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.UIModeBakgroundPreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun RecipeDescriptionRoute(
    onBackClick: () -> Unit,
    recipeId: String,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
    viewModel: RecipeDescriptionViewModel = hiltViewModel()
    ) {
    val toogleRecipeState by viewModel.toogleRecipeState.collectAsStateWithLifecycle()
    val recipeUiState by viewModel.recipeUiState.collectAsStateWithLifecycle()

    RecipeDescriptionScreen(
        toogleState = toogleRecipeState,
        recipeUiState = recipeUiState,
        onToogleFavorite = {
            viewModel.toogleFavorite(recipeId)
        },
        onSelectToogle = viewModel::selectRecipeToogle,
        onBackClick = onBackClick,
        windowSizeClass = windowSizeClass,
    )

    ComposableLifecycle { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.getRecipe(recipeId)
            }
            else -> {}
        }
    }
}

@Composable
fun RecipeDescriptionScreen(
    toogleState: ToogleRecipeState,
    recipeUiState: RecipeUiState,
    onToogleFavorite: () -> Unit = {},
    onSelectToogle: () -> Unit = {},
    onBackClick: () -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    ReceptIaTheme {
        Box {
            Background()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 30.dp),
            ) {
                BackButton(
                    onBackClick = onBackClick,
                    windowSizeClass = windowSizeClass,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(65.dp))
                when (recipeUiState) {
                    RecipeUiState.Loading -> CircularProgressIndicator()
                    is RecipeUiState.Success -> {
                        Header(
                            recipe = recipeUiState.recipe,
                            onToogleFavorite = onToogleFavorite,
                            windowSizeClass = windowSizeClass,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        ToogleButton(
                            toogleState = toogleState,
                            onSelectToogle = onSelectToogle,
                            windowSizeClass = windowSizeClass,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        if (toogleState is ToogleRecipeState.DetailsSelected) {
                            DetailsBody(
                                recipe = recipeUiState.recipe,
                                windowSizeClass = windowSizeClass,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        } else {
                            RecipeBody(
                                recipe = recipeUiState.recipe,
                                windowSizeClass = windowSizeClass,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
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
private fun DetailsScreenPreview() {
    RecipeDescriptionScreen(
        toogleState = ToogleRecipeState.DetailsSelected,
        recipeUiState = RecipeUiState.Success(PreviewParameterData.recipe),
        windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
    )
}

@FontSizeAcessibilityPreview
@UIModeBakgroundPreview
@WindowSizePreview
@Composable
private fun RecipeScreenPreview() {
    RecipeDescriptionScreen(
        toogleState = ToogleRecipeState.RecipeSelected,
        recipeUiState = RecipeUiState.Success(PreviewParameterData.recipe),
        windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
    )
}
