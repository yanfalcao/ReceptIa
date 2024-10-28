package com.nexusfalcao.avatar.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.nexusfalcao.avatar.R
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize

@Composable
fun SelectButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    windowSizeClass: WindowSizeClass,
    onClick: () -> Unit,
) {
    val selectButtonFraction =
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> 1.0f
            WindowWidthSizeClass.MEDIUM -> 0.8f
            WindowWidthSizeClass.EXPANDED -> 0.3f
            else -> 1.0f
        }

    val textStyle = if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.titleLarge
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.headlineSmall
    } else {
        MaterialTheme.typography.headlineLarge
    }

    Button(
        modifier =
        modifier
            .fillMaxWidth(fraction = selectButtonFraction),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary),
        enabled = isSelected,
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(id = R.string.select),
            color = colorScheme.onPrimary,
            style = textStyle,
        )
    }
}