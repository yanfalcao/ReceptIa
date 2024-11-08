package com.nexusfalcao.designsystem.widget.navigationDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.R

@Composable
internal fun DrawerHeader(
    userPhotoId: Int?,
    userName: String?,
    toAvatar: () -> Unit,
) {
    val avatarRes = userPhotoId ?: R.drawable.img_user

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(180.dp)
    ) {
        Image(
            painter = painterResource(id = avatarRes),
            contentDescription = null,
            modifier =
            Modifier
                .height(85.dp)
                .clickable { toAvatar() },
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = userName ?: "",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.width(180.dp),
        )
    }
}