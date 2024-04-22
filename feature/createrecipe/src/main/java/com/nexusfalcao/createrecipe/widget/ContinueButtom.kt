package com.nexusfalcao.createrecipe.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.createrecipe.R
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

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
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        Text(
            text = stringResource(id = R.string.start),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@ThemePreview
@Composable
fun ContinueButtomPreview() {
    ReceptIaTheme {
        ContinueButtom(createRecipe = {})
    }
}
