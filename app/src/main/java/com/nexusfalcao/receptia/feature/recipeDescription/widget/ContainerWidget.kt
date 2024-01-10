package com.nexusfalcao.receptia.feature.recipeDescription.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.ui.theme.Gray100

@Composable
fun Container(
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = Gray100,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(horizontal = 15.dp, vertical = 10.dp),
        content = content,
    )
}
