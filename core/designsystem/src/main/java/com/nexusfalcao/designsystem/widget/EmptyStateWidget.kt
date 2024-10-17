package com.nexusfalcao.designsystem.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.R

@Preview(showBackground = true)
@Composable
fun EmptyStateWidget() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_grocery),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(120.dp),
        )

        Text(
            text = stringResource(id = R.string.empty_title),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier =
                Modifier
                    .widthIn(min = 150.dp, max = 250.dp),
        )

        Text(
            text = stringResource(id = R.string.empty_description),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            modifier =
                Modifier
                    .widthIn(min = 180.dp, max = 280.dp),
        )
    }
}
