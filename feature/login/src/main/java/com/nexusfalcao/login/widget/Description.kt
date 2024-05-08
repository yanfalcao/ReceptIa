package com.nexusfalcao.login.widget

import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nexusfalcao.login.R

@Preview
@Composable
fun Description() {
    Text(
        text = stringResource(id = R.string.description_login),
        color = Color.White,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .width(300.dp),
    )
}
