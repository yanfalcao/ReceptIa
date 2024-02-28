package com.nexusfalcao.receptia.feature.recipeDescription

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nexusfalcao.receptia.ui.preview.PreviewParameterData
import com.nexusfalcao.receptia.feature.recipeDescription.state.RecipeUiState
import com.nexusfalcao.receptia.feature.recipeDescription.state.ToogleRecipeState
import com.nexusfalcao.receptia.feature.recipeDescription.widget.BackButton
import com.nexusfalcao.receptia.feature.recipeDescription.widget.Background
import com.nexusfalcao.receptia.feature.recipeDescription.widget.DetailsBody
import com.nexusfalcao.receptia.feature.recipeDescription.widget.Header
import com.nexusfalcao.receptia.feature.recipeDescription.widget.RecipeBody
import com.nexusfalcao.receptia.feature.recipeDescription.widget.ToogleButton
import com.nexusfalcao.receptia.ui.preview.ThemePreviewShowsBakground
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme

@Composable
internal fun RecipeDescriptionRoute(
    navController: NavController,
    recipeId: String,
) {
    val viewModel = hiltViewModel<RecipeDescriptionViewModel, RecipeDescriptionVMFactory> { factory ->
        factory.create(recipeId)
    }
    val toogleRecipeState by viewModel.toogleRecipeState.collectAsStateWithLifecycle()
    val recipeUiState by viewModel.recipeUiState.collectAsStateWithLifecycle()

    RecipeDescriptionScreen(
        toogleState = toogleRecipeState,
        recipeUiState = recipeUiState,
        onToogleFavorite = viewModel::toogleFavorite,
        onSelectToogle = viewModel::selectRecipeToogle,
        onBackClick = navController::popBackStack,
    )
}

@Composable
private fun RecipeDescriptionScreen(
    toogleState: ToogleRecipeState,
    recipeUiState: RecipeUiState,
    onToogleFavorite: () -> Unit = {},
    onSelectToogle: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    ReceptIaTheme {
        Box {
            Background()
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 30.dp),
            ) {
                BackButton(onBackClick = onBackClick)
                Spacer(modifier = Modifier.height(65.dp))
                when (recipeUiState) {
                    RecipeUiState.Loading -> CircularProgressIndicator()
                    is RecipeUiState.Success -> {
                        Header(
                            recipe = recipeUiState.recipe,
                            onToogleFavorite = onToogleFavorite,
                        )
                        Spacer(modifier = Modifier.height(40.dp))
                        ToogleButton(
                            toogleState = toogleState,
                            onSelectToogle = onSelectToogle,
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        if (toogleState is ToogleRecipeState.DetailsSelected) {
                            DetailsBody(recipeUiState.recipe)
                        } else {
                            RecipeBody(recipeUiState.recipe)
                        }
                    }
                }
            }
        }
    }
}

@ThemePreviewShowsBakground
@Composable
private fun DetailsScreenPreview() {
    RecipeDescriptionScreen(
        toogleState = ToogleRecipeState.DetailsSelected,
        recipeUiState = RecipeUiState.Success(PreviewParameterData.recipe),
    )
}

@ThemePreviewShowsBakground
@Composable
private fun RecipeScreenPreview() {
    RecipeDescriptionScreen(
        toogleState = ToogleRecipeState.RecipeSelected,
        recipeUiState = RecipeUiState.Success(PreviewParameterData.recipe),
    )
}
