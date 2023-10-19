package com.example.receptia.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.receptia.R
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
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_cancel),
                        contentDescription = null,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                            .align(Alignment.CenterVertically),
                    )
                    
                    Spacer(modifier = Modifier.width(10.dp))

                    Text(data.visuals.message)
                }
            }
        }

    }
}