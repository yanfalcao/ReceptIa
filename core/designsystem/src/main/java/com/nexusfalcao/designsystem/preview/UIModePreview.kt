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
annotation class UIModePreview

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
annotation class UIModeBakgroundPreview
