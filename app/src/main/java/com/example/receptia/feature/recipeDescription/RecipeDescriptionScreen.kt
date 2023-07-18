package com.example.receptia.feature.recipeDescription

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
internal fun RecipeDescriptionRoute(
    navController: NavController,
    viewModel: RecipeDescriptionViewModel = viewModel(),
) {
    RecipeDescriptionScreen()
}

@Composable
private fun RecipeDescriptionScreen() {
}
