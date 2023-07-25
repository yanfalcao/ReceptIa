package com.example.receptia.feature.recipeDescription.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.model.Recipe
import com.example.receptia.ui.theme.LightGray

@Composable
fun DetailsBody(recipe: Recipe) {
    // TODO: Add ease icon logic
    val easeIcon = R.drawable.ic_smile

    Column(
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
                Image(
                    painter = painterResource(id = easeIcon),
                    contentDescription = null,
                )

                Text(
                    text = recipe.easeRecipe,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }

        Title(R.string.nutrition)
    }
}

@Composable
private fun Title(@StringRes text: Int) {
    Text(
        text = stringResource(id = text),
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
private fun Container(
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = LightGray,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(15.dp),
        content = content,
    )
}
