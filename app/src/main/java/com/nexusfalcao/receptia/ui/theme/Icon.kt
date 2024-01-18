package com.nexusfalcao.receptia.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.nexusfalcao.receptia.R

@Composable
fun logoIconResource() = when(isSystemInDarkTheme()) {
    true -> {
        painterResource(id = R.drawable.ic_logo_dark)
    }
    false -> {
        painterResource(id = R.drawable.ic_logo)
    }
}