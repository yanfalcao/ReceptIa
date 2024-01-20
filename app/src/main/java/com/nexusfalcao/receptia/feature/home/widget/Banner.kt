package com.nexusfalcao.receptia.feature.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.ui.preview.ThemePreview
import com.nexusfalcao.receptia.ui.theme.Olivine
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme
import com.nexusfalcao.receptia.ui.theme.TeaGreen

@Composable
fun Banner(
    navigateToNewRecipe: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
            .background(
                color = createBannerBackgroundColor(),
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 20.dp,
                bottom = 10.dp,
            ),
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_snacks),
            contentDescription = null,
            modifier = Modifier
                .height(122.dp)
                .width(113.dp)
                .align(Alignment.BottomEnd),
        )

        Column {
            Text(
                text = stringResource(id = R.string.banner_title),
                color = createBannerTextColor(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(230.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = navigateToNewRecipe,
                colors = ButtonDefaults.buttonColors(containerColor = createButtonBackgroundColor()),
            ) {
                Text(
                    text = stringResource(id = R.string.start),
                    color = createButtonTextColor(),
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun createBannerBackgroundColor(): Color {
    return when(isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.primary
        false -> MaterialTheme.colorScheme.secondary
    }
}

@Composable
fun createBannerTextColor(): Color {
    return when(isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.onPrimary
        false -> MaterialTheme.colorScheme.onSecondary
    }
}

@Composable
fun createButtonBackgroundColor(): Color {
    return when(isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.secondary
        false -> MaterialTheme.colorScheme.primary
    }
}

@Composable
fun createButtonTextColor(): Color {
    return when(isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.onSecondary
        false -> MaterialTheme.colorScheme.onPrimary
    }
}

@ThemePreview
@Composable
fun BannerPreview() {
    ReceptIaTheme {
        Banner()
    }
}