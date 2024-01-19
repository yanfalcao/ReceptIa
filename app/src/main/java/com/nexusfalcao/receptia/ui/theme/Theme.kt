package com.nexusfalcao.receptia.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Olivine,
    onPrimary = Color.White,
    secondary = TeaGreen,
    onSecondary = Color.Black,
    background = Gray900,
    onBackground = Color.White,
    surface = Gray800,
    onSurface = Color.White,
    surfaceVariant = Gray700,
    outline = Gray500,
    error = MaximumRed,
    onError = Color.White,
    scrim = Gray600Transparent40
)

private val LightColorScheme = lightColorScheme(
    primary = Olivine,
    onPrimary = Color.White,
    secondary = TeaGreen,
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Gray100,
    onSurface = Color.Black,
    surfaceVariant = Gray100,
    outline = Gray500,
    error = MaximumRed,
    onError = Color.White,
    scrim = BlackTransparent30
)

@Composable
fun ReceptIaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ReceptIaTypography,
        content = content,
    )
}
