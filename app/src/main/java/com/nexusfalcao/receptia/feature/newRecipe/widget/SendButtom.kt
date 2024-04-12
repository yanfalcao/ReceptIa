package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun SendButtom(
    modifier: Modifier,
    onClick:() -> Unit = {},
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(color = colorScheme.secondary, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = colorScheme.onSecondary,
        )
    }
}

@ThemePreview
@Composable
private fun SendButtomPreview() {
    ReceptIaTheme {
        SendButtom(modifier = Modifier.size(40.dp))
    }
}
