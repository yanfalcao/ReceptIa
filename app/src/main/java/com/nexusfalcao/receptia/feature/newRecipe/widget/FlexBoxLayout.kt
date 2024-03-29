package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.nexusfalcao.receptia.ui.preview.ThemePreview
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme

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
                        color = MaterialTheme.colorScheme.secondary,
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
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    modifier = Modifier.size(14.dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    }
}

@ThemePreview
@Composable
fun FlexBoxLayoutPreview(){
    ReceptIaTheme {
        Box (
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(50.dp)
        ){
            FlexBoxLayout(
                modifier = Modifier,
                ingredientUiState = IngredientUiState(
                    ingredients = listOf("Macarrão", "Cogumelo"),
                    state = RecipeFieldState.FAVORITE
                ),
                onRemoveIngredient = {_, _ ->}
            )
        }
    }
}
