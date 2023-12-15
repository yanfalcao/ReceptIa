package com.nexusfalcao.receptia.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.ui.theme.Green

@Composable
fun OutlineCircularShape(
    modifier: Modifier = Modifier,
    shapeSize: Dp,
    stroke: Float = 10F,
    color: Color = Green,
) {
    Canvas(modifier = modifier.size(shapeSize)) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawCircle(
            color = color,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = size.minDimension/2,
            style = Stroke(stroke)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OutlineCircularShapePreview() {
    OutlineCircularShape(shapeSize = 150.dp)
}
