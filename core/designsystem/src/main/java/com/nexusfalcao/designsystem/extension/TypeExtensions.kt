package com.nexusfalcao.designsystem.extension

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.window.core.layout.WindowSizeClass

@Composable
fun Typography.scaleTitleLargeBy(windowSizeClass: WindowSizeClass): TextStyle {
    return if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.titleLarge
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.headlineSmall
    } else {
        MaterialTheme.typography.headlineLarge
    }
}