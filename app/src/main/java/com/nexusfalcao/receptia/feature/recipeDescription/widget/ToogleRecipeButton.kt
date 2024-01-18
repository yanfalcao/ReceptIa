package com.nexusfalcao.receptia.feature.recipeDescription.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.feature.recipeDescription.state.ToogleRecipeState
import com.nexusfalcao.receptia.ui.preview.ThemePreview
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme

@Composable
fun ToogleButton(
    modifier: Modifier = Modifier,
    toogleState: ToogleRecipeState,
    onSelectToogle: () -> Unit = {},
) {
    val cornerShape = RoundedCornerShape(30.dp)
    val isSelectedDetails = toogleState is ToogleRecipeState.DetailsSelected

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = cornerShape
            )
            .fillMaxWidth()
            .height(50.dp)
            .clip(cornerShape)
            .clickable { onSelectToogle() },
    ) {
        val modifierToogle = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.5f)

        Box(
            modifier = modifierToogle
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = cornerShape
                )
                .align(if (!isSelectedDetails) Alignment.TopStart else Alignment.TopEnd),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(
                    id = if (!isSelectedDetails) R.string.details else R.string.recipe,
                ),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Box(
            modifier = modifierToogle
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = cornerShape
                )
                .align(if (isSelectedDetails) Alignment.TopStart else Alignment.TopEnd),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(
                    id = if (isSelectedDetails) R.string.details else R.string.recipe,
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@ThemePreview
@Composable
private fun ToogleButtonDetailsPreview() {
    ReceptIaTheme {
        ToogleButton(
            toogleState = ToogleRecipeState.DetailsSelected,
        )
    }
}

@ThemePreview
@Composable
private fun ToogleButtonRecipePreview() {
    ReceptIaTheme {
        ToogleButton(
            toogleState = ToogleRecipeState.RecipeSelected,
        )
    }
}