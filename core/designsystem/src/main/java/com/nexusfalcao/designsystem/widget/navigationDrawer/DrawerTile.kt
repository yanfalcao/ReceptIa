package com.nexusfalcao.designsystem.widget.navigationDrawer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun DrawerTile(
    @DrawableRes iconResourceId: Int,
    @StringRes titleResourceId: Int,
    onClick: () -> Unit = {},
) {

    Row(
        modifier =
        Modifier
            .width(180.dp)
            .heightIn(min = 50.dp, max = 80.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = iconResourceId),
            contentDescription = null,
            modifier = Modifier.width(26.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = stringResource(id = titleResourceId),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}