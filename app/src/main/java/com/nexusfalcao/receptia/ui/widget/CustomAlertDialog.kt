package com.nexusfalcao.receptia.ui.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nexusfalcao.receptia.R

@Composable
fun CustomAlertDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info") },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}

@Composable
fun CustomUpdateDialog(
    title: String,
    description: String,
    onUpdate: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        icon = { Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info") },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = description)
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = onUpdate
            ) {
                Text(stringResource(id = R.string.update))
            }
        }
    )
}