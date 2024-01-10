package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.feature.newRecipe.state.RecipeFieldState
import com.nexusfalcao.receptia.feature.newRecipe.state.IngredientUiState
import com.nexusfalcao.receptia.ui.theme.TeaGreen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlexBoxLayout(
    modifier: Modifier,
    ingredientUiState: IngredientUiState,
    onRemoveIngredient: (RecipeFieldState, String) -> Unit,
) {
    val ingredientList = if (ingredientUiState.ingredients.isEmpty()) {
        return
    } else {
        ingredientUiState.ingredients
    }

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        for (ingredient in ingredientList) {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .background(
                        color = TeaGreen,
                        shape = RoundedCornerShape(20.dp),
                    )
                    .padding(vertical = 6.dp, horizontal = 8.dp)
                    .clickable {
                        onRemoveIngredient(ingredientUiState.state, ingredient)
                    },
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = ingredient,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    modifier = Modifier.size(14.dp),
                    contentDescription = null,
                )
            }
        }
    }
}
