package com.example.receptia.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.receptia.R
import com.example.receptia.persistence.utils.DifficultState

@Composable
fun DifficultIcon(
    difficultState: DifficultState,
    modifier: Modifier = Modifier,
) {
    val difficultIcon = when (difficultState) {
        DifficultState.Easy -> R.drawable.ic_smile
        DifficultState.Medium -> R.drawable.ic_surprised
        DifficultState.Hard -> R.drawable.ic_sad
    }

    Image(
        painter = painterResource(id = difficultIcon),
        contentDescription = null,
        modifier = modifier,
    )
}
