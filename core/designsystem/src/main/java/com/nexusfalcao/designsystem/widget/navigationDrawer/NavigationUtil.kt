package com.nexusfalcao.designsystem.widget.navigationDrawer

import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

internal object NavigationUtil {
    fun getNavigationMode(
        isNavigationEnabled: Boolean = true,
        windowSizeClass: WindowSizeClass
    ): NavigationMode {
        return if (isNavigationEnabled) {
            if (shouldShowPermanentNavigation(windowSizeClass)) {
                NavigationMode.PERMANENT
            } else {
                NavigationMode.MODAL
            }
        } else {
            NavigationMode.NONE
        }
    }

    private fun shouldShowPermanentNavigation(windowSizeClass: androidx.window.core.layout.WindowSizeClass): Boolean {
        val isWidthExpanded = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED
        val isWidthMedium = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM

        return isWidthMedium || isWidthExpanded
    }
}