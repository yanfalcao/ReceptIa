package com.nexusfalcao.designsystem.extension

import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

fun WindowSizeClass.hasCompactSize() : Boolean {
    return windowWidthSizeClass == WindowWidthSizeClass.COMPACT ||
            windowHeightSizeClass == WindowHeightSizeClass.COMPACT
}

fun WindowSizeClass.hasMediumSize() : Boolean {
    return windowWidthSizeClass == WindowWidthSizeClass.MEDIUM ||
            windowHeightSizeClass == WindowHeightSizeClass.MEDIUM
}

fun WindowSizeClass.hasExpandedSize() : Boolean {
    return windowWidthSizeClass == WindowWidthSizeClass.EXPANDED ||
            windowHeightSizeClass == WindowHeightSizeClass.EXPANDED
}