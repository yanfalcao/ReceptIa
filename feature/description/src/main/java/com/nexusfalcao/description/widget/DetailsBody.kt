package com.nexusfalcao.description.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.description.R
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.DifficultIcon
import com.nexusfalcao.model.Recipe

@Composable
fun DetailsBody(recipe: Recipe) {
    val amountPeopleServes = recipe.recipeDetails.amountPeopleServes

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Title(R.string.description)

        Container {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = recipe.description,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Title(R.string.prep_time)

        Container {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                )

                Text(
                    text = recipe.recipeDetails.preparationTime,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Title(R.string.easeRecipe)

        Container {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                DifficultIcon(
                    recipeDifficult = recipe.recipeDetails.recipeDifficult,
                )

                Text(
                    text = recipe.recipeDetails.difficult,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Title(R.string.serves_up)

        Container {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dish),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                )

                Text(
                    text = "$amountPeopleServes ${pluralStringResource(R.plurals.people, amountPeopleServes)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Title(R.string.nutrition)

        Container {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                NutritionTile(
                    title = R.string.calories,
                    description = recipe.recipeDetails.amountCalories,
                )

                NutritionTile(
                    title = R.string.carbs,
                    description = recipe.recipeDetails.amountCarbs,
                )

                NutritionTile(
                    title = R.string.proteins,
                    description = recipe.recipeDetails.amountProteins,
                )
            }
        }
    }
}

@Composable
private fun NutritionTile(
    @StringRes title: Int,
    description: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(title),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )

        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@UIModePreview
@Composable
fun DetailsBodyPreview() {
    ReceptIaTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            DetailsBody(PreviewParameterData.recipe)
        }
    }
}
