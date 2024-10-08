package com.nexusfalcao.designsystem.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.R
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.theme.AlertSnackbarColor

object CustomSnackbar {
    @Composable
    fun Error(hostState: SnackbarHostState) {
        DefaultSnackbar(
            hostState = hostState,
            backgroundColor = MaterialTheme.colorScheme.error,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_cancel),
                contentDescription = null,
                modifier = Modifier
                    .height(28.dp)
                    .width(28.dp),
            )
        }
    }

    @Composable
    fun Alert(hostState: SnackbarHostState) {
        DefaultSnackbar(
            hostState = hostState,
            backgroundColor = AlertSnackbarColor,
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                modifier = Modifier
                    .height(28.dp)
                    .width(28.dp),
            )
        }
    }

    @Composable
    fun DefaultSnackbar(
        hostState: SnackbarHostState,
        backgroundColor: Color,
        contentIcon: @Composable RowScope.() -> Unit
    ) {
        SnackbarHost(hostState = hostState) { data ->
            Snackbar(
                modifier = Modifier.padding(12.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = backgroundColor,
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    contentIcon()

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(data.visuals.message)
                }
            }
        }
    }
}

@FontSizeAcessibilityPreview
@Preview(showBackground = true,)
@Composable
private fun DefaultSnackbarPreview() {
    Snackbar(
        modifier = Modifier.padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        containerColor = AlertSnackbarColor,
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                modifier = Modifier
                    .height(28.dp)
                    .width(28.dp),
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text("jfdskfbsf skdbsjdkfbsjd sdkjfjbsdbjfks kjdsbfksdjbf")
        }
    }
}
