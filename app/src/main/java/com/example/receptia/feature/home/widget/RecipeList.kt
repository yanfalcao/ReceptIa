package com.example.receptia.feature.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.feature.home.preview.RecipesPreviewParameterProvider
import com.example.receptia.persistence.Recipe
import com.example.receptia.ui.theme.LightGray

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
    val bookmarkIcon = if (recipe.isFavorite) {
        R.drawable.ic_bookmark_green
    } else {
        R.drawable.ic_bookmark
    }

    // TODO: Add ease icon logic
    val easeIcon = R.drawable.ic_smile

    Box(
        modifier = Modifier
            .background(
                color = LightGray,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .clickable {
                navigateToDescription(recipe.id)
            }
            .padding(start = 15.dp, end = 25.dp)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = bookmarkIcon),
            contentDescription = null,
        )

        Text(
            text = recipe.name,
            color = Color.Black,
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
            )

            Text(
                text = recipe.prepTime,
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp),
            )

            Image(
                painter = painterResource(id = easeIcon),
                contentDescription = null,
                modifier = Modifier.padding(start = 50.dp),
            )

            Text(
                text = recipe.easeRecipe,
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Preview
@Composable
private fun RecipeListPreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    RecipeList(
        recipes = recipes,
        navigateToDescription = {},
    )
}
