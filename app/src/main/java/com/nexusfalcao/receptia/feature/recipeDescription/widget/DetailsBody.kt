package com.nexusfalcao.receptia.feature.recipeDescription.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.persistence.Recipe
import com.nexusfalcao.receptia.ui.widget.DifficultIcon

@Composable
fun DetailsBody(recipe: Recipe) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Title(R.string.description)

        Container {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = recipe.description,
                style = MaterialTheme.typography.labelSmall,
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
                )

                Text(
                    text = recipe.prepTime,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }

        Title(R.string.easeRecipe)

        Container {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                DifficultIcon(
                    difficultState = recipe.difficultState,
                )

                Text(
                    text = recipe.difficult,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }

        Title(R.string.amount_people_serves)

        Container {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_dish),
                    contentDescription = null,
                )

                Text(
                    text = recipe.amountPeopleServes.toString() + " " + stringResource(R.string.people),
                    style = MaterialTheme.typography.labelSmall,
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
                    description = recipe.amountCalories,
                )

                NutritionTile(
                    title = R.string.carbs,
                    description = recipe.amountCarbs,
                )

                NutritionTile(
                    title = R.string.proteins,
                    description = recipe.amountProteins,
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
            style = MaterialTheme.typography.labelMedium,
        )

        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}
