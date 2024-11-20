package com.nexusfalcao.designsystem.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.designsystem.R
import com.nexusfalcao.designsystem.extension.scaleBodyMediumBy
import com.nexusfalcao.designsystem.extension.scaleTitleMediumBy

@Preview(showBackground = true)
@Composable
fun EmptyPaneWidget(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
) {
    Column(
    verticalArrangement = Arrangement.spacedBy(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_man_with_food),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(150.dp),
        )

        Text(
            text = stringResource(id = R.string.empty_pane_title),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = Typography.scaleTitleMediumBy(windowSizeClass),
            modifier =
            Modifier
                .widthIn(min = 150.dp, max = 250.dp),
        )

        Text(
            text = stringResource(id = R.string.empty_pane_description),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = Typography.scaleBodyMediumBy(windowSizeClass),
            modifier =
            Modifier
                .widthIn(min = 180.dp, max = 280.dp),
        )
    }
}
