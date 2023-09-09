package com.example.receptia.feature.recipeDescription

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.feature.recipeDescription.preview.PreviewParameterData
import com.example.receptia.feature.recipeDescription.state.RecipeUiState
import com.example.receptia.feature.recipeDescription.state.ToogleRecipeState
import com.example.receptia.feature.recipeDescription.widget.BackButton
import com.example.receptia.feature.recipeDescription.widget.Background
import com.example.receptia.feature.recipeDescription.widget.DetailsBody
import com.example.receptia.feature.recipeDescription.widget.Header
import com.example.receptia.feature.recipeDescription.widget.RecipeBody
import com.example.receptia.feature.recipeDescription.widget.ToogleButton
import com.example.receptia.ui.theme.ReceptIaTheme

@Composable
internal fun RecipeDescriptionRoute(
    navController: NavController,
    recipeId: String,
    viewModel: RecipeDescriptionViewModel = viewModel(factory = RecipeDescriptionVMFactory(recipeId)),
) {
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

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeDescriptionScreenPreview() {
    RecipeDescriptionScreen(
        toogleState = ToogleRecipeState.DetailsSelected,
        recipeUiState = RecipeUiState.Success(PreviewParameterData.recipe),
    )
}
