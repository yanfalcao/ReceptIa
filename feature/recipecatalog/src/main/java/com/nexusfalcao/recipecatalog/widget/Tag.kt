package com.nexusfalcao.recipecatalog.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun Tag(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    isSelected: Boolean,
    updateTagFilter: () -> Unit = {},
) {
    val cornerShape = RoundedCornerShape(30.dp)
    val colorScheme = MaterialTheme.colorScheme
    val textColor = when(isSelected) {
        true -> colorScheme.onPrimary
        false -> colorScheme.onBackground
    }
    val backgroundColor = when(isSelected) {
        true -> colorScheme.primary
        false -> colorScheme.onBackground
    }

    val modifier = when(isSelected) {
        true -> Modifier.background(color = backgroundColor, shape = cornerShape)
        false -> Modifier.border(width = 1.dp, color = backgroundColor, shape = cornerShape)
    }

    Box(
        modifier = modifier
            .clickable { updateTagFilter() }
            .padding(vertical = 5.dp, horizontal = 15.dp),
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}
