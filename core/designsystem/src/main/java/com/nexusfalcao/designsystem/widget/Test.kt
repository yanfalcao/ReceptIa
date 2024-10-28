package com.nexusfalcao.designsystem.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass

@Composable
fun BackgroundExample(windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass) {
    val backgroundColor =
        when (windowSizeClass.windowHeightSizeClass) {
            WindowHeightSizeClass.COMPACT -> Color.Red
            WindowHeightSizeClass.MEDIUM -> Color.Green
            else -> Color.Blue
        }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
    )
}

@Composable
@PreviewScreenSizes
fun PreviewExample() {
    val configuration = LocalConfiguration.current
    val windowSizeClass = WindowSizeClass.compute(
        dpHeight = configuration.screenHeightDp.toFloat(),
        dpWidth = configuration.screenWidthDp.toFloat(),
    )

    BackgroundExample(windowSizeClass)
}
