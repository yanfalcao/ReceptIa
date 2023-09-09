package com.example.receptia.feature.historic.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.receptia.ui.theme.Green

@Composable
fun Tag(text: String) {
    // TODO: Implement logic
    Box(
        modifier = Modifier
            .background(
                color = Green,
                shape = RoundedCornerShape(30.dp),
            )
            .padding(vertical = 5.dp, horizontal = 15.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
        )
    }
}
