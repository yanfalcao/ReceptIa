package com.nexusfalcao.recipecatalog.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.widget.DifficultIcon
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.recipecatalog.R

@Composable
fun GridList(
    recipes: List<Recipe>,
    navigateToDescription: (String) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 15.dp,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(recipes) {
            GridTile(
                recipe = it,
                navigateToDescription = navigateToDescription,
            )
        }
    }
}

@Composable
private fun GridTile(
    recipe: Recipe,
    navigateToDescription: (String) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(15.dp),
                )
                .clickable { navigateToDescription(recipe.id) }
                .fillMaxWidth()
                .heightIn(min = 120.dp, max = 300.dp)
                .padding(horizontal = 10.dp, vertical = 15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = recipe.name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )

            Text(
                text = recipe.recipeDetails.preparationTime,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 5.dp),
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            DifficultIcon(
                recipeDifficult = recipe.recipeDetails.recipeDifficult,
                modifier = Modifier.size(16.dp),
            )

            Text(
                text = recipe.recipeDetails.difficult,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 5.dp),
            )
        }
    }
}

@FontSizeAcessibilityPreview
@Composable
fun GridListPreview() {
    GridList(
        recipes =
            listOf(
                PreviewParameterData.recipe,
                PreviewParameterData.recipe,
                PreviewParameterData.recipe,
                PreviewParameterData.recipe,
            ),
        navigateToDescription = {},
    )
}
