package com.nexusfalcao.description.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.description.R
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.model.Recipe

@Composable
fun RecipeBody(recipe: Recipe) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Title(
            text = stringResource(R.string.ingredients_amount, recipe.ingredients.size),
        )

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            for (ingredient in recipe.ingredients) {
                Container {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = ingredient.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(4f),
                        )

                        Text(
                            text = ingredient.measure,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }
        }

        Title(R.string.praparation)

        Container {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = recipe.stepsToString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@UIModePreview
@Composable
fun RecipeBodyPreview() {
    ReceptIaTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            RecipeBody(PreviewParameterData.recipe)
        }
    }
}
