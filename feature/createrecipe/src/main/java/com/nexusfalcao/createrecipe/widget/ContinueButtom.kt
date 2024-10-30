package com.nexusfalcao.createrecipe.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.nexusfalcao.createrecipe.R
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.designsystem.extension.scaleTitleLargeBy
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun ContinueButtom(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    createRecipe: () -> Unit,
) {
    val selectButtonFraction =
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> 1.0f
            WindowWidthSizeClass.MEDIUM -> 0.8f
            WindowWidthSizeClass.EXPANDED -> 0.3f
            else -> 1.0f
        }
    val textStyle = Typography.scaleTitleLargeBy(windowSizeClass = windowSizeClass)

    Button(
        onClick = {
            createRecipe()
        },
        modifier = modifier.fillMaxWidth(fraction = selectButtonFraction),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Text(
            modifier = Modifier.padding(6.dp),
            text = stringResource(id = R.string.start),
            color = MaterialTheme.colorScheme.onPrimary,
            style = textStyle,
        )
    }
}

@UIModePreview
@Composable
fun ContinueButtomPreview() {
    ReceptIaTheme {
        ContinueButtom(
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            createRecipe = {},
        )
    }
}
