package com.nexusfalcao.receptia.feature.historic.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.ui.theme.BlackTransparent05
import com.nexusfalcao.receptia.ui.theme.Gray100

@Composable
fun FilterButton(
    modifier: Modifier,
    backgrounColor: Color = Gray100,
    onClick:() -> Unit
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(color = backgrounColor, shape = CircleShape)
            .shadow(
                elevation = 4.dp,
                spotColor = BlackTransparent05,
                ambientColor = BlackTransparent05,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_tune),
            contentDescription = null,
        )
    }
}
