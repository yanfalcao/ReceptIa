package com.nexusfalcao.recipecatalog.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.AmbientShadowColor
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.theme.SpotShadowColor
import com.nexusfalcao.recipecatalog.R

@Composable
fun FilterButton(
    modifier: Modifier,
    hasAnyFilterSelected: Boolean,
    onClick:() -> Unit
) {
    val backgroundColor = when(hasAnyFilterSelected) {
        true -> MaterialTheme.colorScheme.primary
        false -> MaterialTheme.colorScheme.surface
    }
    val imageColor = when(hasAnyFilterSelected) {
        true -> MaterialTheme.colorScheme.onPrimary
        false -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(color = backgroundColor, shape = CircleShape)
            .shadow(
                elevation = 4.dp,
                spotColor = SpotShadowColor,
                ambientColor = AmbientShadowColor,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_tune),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = imageColor)
        )
    }
}

@ThemePreview
@Composable
private fun FilterButtonPreview() {
    ReceptIaTheme {
        FilterButton(
            modifier = Modifier.size(40.dp),
            hasAnyFilterSelected = false,
        ) {}
    }
}

@ThemePreview
@Composable
private fun FilterButtonSelectedPreview() {
    ReceptIaTheme {
        FilterButton(
            modifier = Modifier.size(40.dp),
            hasAnyFilterSelected = true,
        ) {}
    }
}
