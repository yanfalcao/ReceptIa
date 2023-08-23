package com.example.receptia.feature.recipeDescription.widget

import androidx.compose.foundation.layout.Arrangement
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
import com.example.receptia.R
import com.example.receptia.persistence.Recipe

@Composable
fun RecipeBody(recipe: Recipe) {
    // TODO: Finish Recipe Body
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
                            style = MaterialTheme.typography.labelMedium,
                        )

                        Text(
                            text = ingredient.measure,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }

        Title(R.string.praparation)

        Container {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = recipe.recipeSteps,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
