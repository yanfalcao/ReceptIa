package com.nexusfalcao.description.widget

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun Title(@StringRes text: Int) {
    Title(text = stringResource(id = text))
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleSmall,
    )
}
