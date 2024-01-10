package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.ui.theme.Olivine

@Composable
fun ContinueButtom(
    createRecipe: () -> Unit,
) {
    Button(
        onClick = {
            createRecipe()
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Olivine),
    ) {
        Text(
            text = stringResource(id = R.string.start),
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}
