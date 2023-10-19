package com.example.receptia.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.receptia.ui.theme.RedAlert

object CustomSnackbar {
    @Composable
    fun Error(hostState: SnackbarHostState) {
        SnackbarHost(hostState = hostState) { data ->
            Snackbar(
                modifier = Modifier.padding(12.dp),
                shape = RoundedCornerShape(12.dp),
                containerColor = RedAlert,
            ) {
                Text(data.visuals.message)
            }
        }

    }
}