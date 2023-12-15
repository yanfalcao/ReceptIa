package com.nexusfalcao.receptia.feature.login.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.ui.theme.Green

@Preview
@Composable
fun LoadingButton() {
    Box(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 30.dp),
            )
            .height(55.dp)
            .width(55.dp),
    ) {
        CircularProgressIndicator(
            color = Green,
            modifier = Modifier
                .align(Alignment.Center)
                .height(40.dp)
                .width(40.dp),
        )
    }
}
