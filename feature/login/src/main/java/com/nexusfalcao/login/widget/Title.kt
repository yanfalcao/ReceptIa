package com.nexusfalcao.login.widget

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.login.R

@Preview
@Composable
fun Title(windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass) {
    val width = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 300.dp
        WindowWidthSizeClass.MEDIUM -> 400.dp
        else -> 500.dp
    }

    Text(
        text = stringResource(id = R.string.title_login),
        color = Color.White,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.width(width),
    )
}
