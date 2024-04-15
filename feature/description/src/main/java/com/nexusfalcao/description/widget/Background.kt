package com.nexusfalcao.description.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.nexusfalcao.description.R
import com.nexusfalcao.designsystem.preview.ThemePreviewShowsBakground
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.theme.RecipeDescriptionBackgroundColor

@Composable
fun Background() {
    val colorStops = arrayOf(
        0.0f to Color.Transparent,
        0.93f to MaterialTheme.colorScheme.background,
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RecipeDescriptionBackgroundColor),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_background_recipe),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.26f)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(colorStops = colorStops),
                ),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(Alignment.BottomCenter)
                .background(color = MaterialTheme.colorScheme.background),
        )
    }
}

@ThemePreviewShowsBakground
@Composable
fun BackgroundPreview() {
    ReceptIaTheme {
        Background()
    }
}