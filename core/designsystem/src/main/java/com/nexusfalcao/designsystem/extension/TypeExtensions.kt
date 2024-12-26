package com.nexusfalcao.designsystem.extension

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.window.core.layout.WindowSizeClass

@Composable
fun Typography.scaleHeadlineSmallBy(windowSizeClass: WindowSizeClass): TextStyle {
    return if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.headlineSmall
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.headlineMedium
    } else {
        MaterialTheme.typography.headlineLarge
    }
}

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

@Composable
fun Typography.scaleTitleMediumBy(windowSizeClass: WindowSizeClass): TextStyle {
    return if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.titleMedium
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.titleLarge
    } else {
        MaterialTheme.typography.headlineMedium
    }
}

@Composable
fun Typography.scaleLabelLargeBy(windowSizeClass: WindowSizeClass): TextStyle {
    return if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.labelLarge
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.bodyLarge
    } else {
        MaterialTheme.typography.titleSmall
    }
}

@Composable
fun Typography.scaleLabelMediumBy(windowSizeClass: WindowSizeClass): TextStyle {
    return if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.labelMedium
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.labelLarge
    } else {
        MaterialTheme.typography.bodyMedium
    }
}

@Composable
fun Typography.scaleBodyLargeBy(windowSizeClass: WindowSizeClass): TextStyle {
    return if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.bodyLarge
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.titleMedium
    } else {
        MaterialTheme.typography.titleLarge
    }
}

@Composable
fun Typography.scaleBodyMediumBy(windowSizeClass: WindowSizeClass): TextStyle {
    return if (windowSizeClass.hasCompactSize()) {
        MaterialTheme.typography.bodyMedium
    } else if (windowSizeClass.hasMediumSize()) {
        MaterialTheme.typography.bodyLarge
    } else {
        MaterialTheme.typography.titleLarge
    }
}