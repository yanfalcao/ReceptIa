package com.nexusfalcao.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.home.preview.RecipesPreviewParameterProvider
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.DifficultIcon
import com.nexusfalcao.home.R

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    navigateToDescription: (String) -> Unit,
) {
    LazyColumn {
        items(recipes) {
            RecipeListTile(
                recipe = it,
                navigateToDescription = navigateToDescription,
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
private fun RecipeListTile(
    recipe: Recipe,
    navigateToDescription: (String) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val bookmarkColor = if (recipe.isFavorite) {
        colorScheme.primary
    } else {
        colorScheme.surfaceTint
    }
    val colorFilter = when(isSystemInDarkTheme()) {
        true -> ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        false -> null
    }

    Box(
        modifier = Modifier
            .background(
                color = colorScheme.surface,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .clickable {
                navigateToDescription(recipe.id)
            }
            .padding(start = 15.dp, end = 25.dp)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bookmark),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = bookmarkColor)
        )

        Text(
            text = recipe.name,
            color = colorScheme.onSurface,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(start = 40.dp, top = 12.dp),
        )

        Row(
            modifier = Modifier
                .padding(top = 75.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = null,
                colorFilter = colorFilter,
            )

            Text(
                text = recipe.recipeDetails.preparationTime,
                color = colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp),
            )

            DifficultIcon(
                recipeDifficult = recipe.recipeDetails.recipeDifficult,
                modifier = Modifier.padding(start = 50.dp),
            )

            Text(
                text = recipe.recipeDetails.difficult,
                color = colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@ThemePreview
@Composable
private fun RecipeListPreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    ReceptIaTheme {
        RecipeList(
            recipes = recipes,
            navigateToDescription = {},
        )
    }
}
