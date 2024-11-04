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
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.description.R
import com.nexusfalcao.designsystem.extension.scaleBodyMediumBy
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.DifficultIcon
import com.nexusfalcao.model.Recipe

@Composable
fun DetailsBody(
    recipe: Recipe,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    val amountPeopleServes = recipe.recipeDetails.amountPeopleServes

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Title(
            text = R.string.description,
            windowSizeClass = windowSizeClass,
        )

        Container {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = recipe.description,
                color = MaterialTheme.colorScheme.onSurface,
                style = Typography.scaleBodyMediumBy(windowSizeClass),
            )
        }

        Title(
            text = R.string.prep_time,
            windowSizeClass = windowSizeClass,
        )

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
                    style = Typography.scaleBodyMediumBy(windowSizeClass),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Title(
            text = R.string.easeRecipe,
            windowSizeClass = windowSizeClass,
        )

        Container {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                DifficultIcon(
                    recipeDifficult = recipe.recipeDetails.recipeDifficult,
                )

                Text(
                    text = recipe.recipeDetails.difficult,
                    style = Typography.scaleBodyMediumBy(windowSizeClass),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Title(
            text = R.string.serves_up,
            windowSizeClass = windowSizeClass,
        )

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
                    style = Typography.scaleBodyMediumBy(windowSizeClass),
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        Title(
            text = R.string.nutrition,
            windowSizeClass = windowSizeClass,
        )

        Container {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                NutritionTile(
                    title = R.string.calories,
                    description = recipe.recipeDetails.amountCalories,
                    windowSizeClass = windowSizeClass,
                )

                NutritionTile(
                    title = R.string.carbs,
                    description = recipe.recipeDetails.amountCarbs,
                    windowSizeClass = windowSizeClass,
                )

                NutritionTile(
                    title = R.string.proteins,
                    description = recipe.recipeDetails.amountProteins,
                    windowSizeClass = windowSizeClass,
                )
            }
        }
    }
}

@Composable
private fun NutritionTile(
    @StringRes title: Int,
    description: String,
    windowSizeClass: WindowSizeClass,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(title),
            color = MaterialTheme.colorScheme.onSurface,
            style = Typography.scaleBodyMediumBy(windowSizeClass),
        )

        Text(
            text = description,
            color = MaterialTheme.colorScheme.onSurface,
            style = Typography.scaleBodyMediumBy(windowSizeClass),
        )
    }
}

@UIModePreview
@Composable
fun DetailsBodyPreview() {
    ReceptIaTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            DetailsBody(
                recipe = PreviewParameterData.recipe,
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }
    }
}
