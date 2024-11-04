package com.nexusfalcao.description.widget

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.designsystem.extension.scaleTitleMediumBy

@Composable
fun Title(
    @StringRes text: Int,
    windowSizeClass: WindowSizeClass,
) {
    Title(
        text = stringResource(id = text),
        windowSizeClass = windowSizeClass,
    )
}

@Composable
fun Title(
    text: String,
    windowSizeClass: WindowSizeClass,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = Typography.scaleTitleMediumBy(windowSizeClass),
    )
}
