package com.nexusfalcao.designsystem.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nexusfalcao.designsystem.R
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

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
        icon = { Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Info",
            tint = MaterialTheme.colorScheme.primary
        )},
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        text = {
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = onUpdate
            ) {
                Text(
                    stringResource(id = R.string.update),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    )
}

@FontSizeAcessibilityPreview
@UIModePreview
@Composable
private fun UpdatePreview() {
    ReceptIaTheme {
        CustomUpdateDialog(
            title = "Atualize o app",
            description = "Nós recomendamos que você atualize o aplicativo para a última versão. Após a atualização você pode continuar usando o aplicativo normalmente.",
            onUpdate = {},
        )
    }
}