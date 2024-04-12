package com.nexusfalcao.receptia.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.nexusfalcao.model.state.RecipeDifficult
import com.nexusfalcao.receptia.R

@Composable
fun DifficultIcon(
    difficultState: RecipeDifficult,
    modifier: Modifier = Modifier,
) {
    val colorFilter = when(isSystemInDarkTheme()) {
        true -> ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        false -> null
    }
    val difficultIcon = when (difficultState) {
        RecipeDifficult.Easy -> R.drawable.ic_smile
        RecipeDifficult.Medium -> R.drawable.ic_surprised
        RecipeDifficult.Hard -> R.drawable.ic_sad
    }

    Image(
        painter = painterResource(id = difficultIcon),
        contentDescription = null,
        modifier = modifier,
        colorFilter = colorFilter
    )
}
