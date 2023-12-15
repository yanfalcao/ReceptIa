package com.nexusfalcao.receptia.feature.historic.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.ui.theme.Green
import com.nexusfalcao.receptia.ui.theme.LightGray

@Composable
fun Tag(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    isSelected: Boolean,
    updateTagFilter: () -> Unit = {},
) {
    val backgroundColor = when(isSelected) {
        true -> Green
        false -> LightGray
    }
    val textColor = when(isSelected) {
        true -> Color.White
        false -> Color.Black
    }

    Box(
        modifier = Modifier
            .clickable { updateTagFilter() }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(30.dp),
            )
            .padding(vertical = 5.dp, horizontal = 15.dp),
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}
