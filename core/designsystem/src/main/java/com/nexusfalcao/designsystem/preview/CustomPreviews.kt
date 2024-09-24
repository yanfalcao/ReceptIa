package com.nexusfalcao.designsystem.preview

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark Mode",
    group = "Light and Dark Mode",
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Light Mode",
    group = "Light and Dark Mode",
    uiMode = UI_MODE_NIGHT_NO,
)
annotation class ThemePreview

@Preview(
    name = "Dark Mode",
    group = "Light and Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Light Mode",
    group = "Light and Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_NO,
)
annotation class ThemePreviewShowsBakground

@Preview(
    name = "Scale 100%",
    group = "Font Size Accessibility",
    fontScale = 1f,
)
@Preview(
    name = "Scale 115%",
    group = "Font Size Accessibility",
    fontScale = 1.15f,
)
@Preview(
    name = "Scale 130%",
    group = "Font Size Accessibility",
    fontScale = 1.3f,
)
@Preview(
    name = "Scale 150%",
    group = "Font Size Accessibility",
    fontScale = 1.5f,
)
@Preview(
    name = "Scale 180%",
    group = "Font Size Accessibility",
    fontScale = 1.8f,
)
@Preview(
    name = "Scale 200%",
    group = "Font Size Accessibility",
    fontScale = 2f,
)
annotation class FontSizeAcessibilityPreview
