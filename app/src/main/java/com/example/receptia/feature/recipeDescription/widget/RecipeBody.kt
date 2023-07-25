package com.example.receptia.feature.recipeDescription.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.receptia.model.Recipe

@Composable
fun RecipeBody(recipe: Recipe) {
    // TODO: Finish Recipe Body
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Receita",
            style = MaterialTheme.typography.labelSmall,
        )
    }
}
