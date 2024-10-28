package com.nexusfalcao.designsystem.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowSizeClass

object UtilPreview {
    @Composable
    fun getPreviewWindowSizeClass(): WindowSizeClass {
        val configuration = LocalConfiguration.current
        return WindowSizeClass.compute(
            dpHeight = configuration.screenHeightDp.toFloat(),
            dpWidth = configuration.screenWidthDp.toFloat(),
        )
    }
}
