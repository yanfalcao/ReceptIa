package com.example.receptia.feature.recipeDescription

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.feature.recipeDescription.state.ToogleRecipeState
import com.example.receptia.feature.recipeDescription.widget.BackButton
import com.example.receptia.feature.recipeDescription.widget.Background
import com.example.receptia.feature.recipeDescription.widget.ToogleButton
import com.example.receptia.ui.theme.ReceptIaTheme

@Composable
internal fun RecipeDescriptionRoute(
    viewModel: RecipeDescriptionViewModel = viewModel(),
) {
    val toogleRecipeState by viewModel.toogleRecipeState.collectAsStateWithLifecycle()

    RecipeDescriptionScreen(
        toogleState = toogleRecipeState,
        onSelectToogle = viewModel::selectRecipeToogle,
    )
}

@Composable
private fun RecipeDescriptionScreen(
    toogleState: ToogleRecipeState,
    onSelectToogle: () -> Unit = {},
) {
    ReceptIaTheme {
        Box {
            Background()
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(horizontal = 25.dp, vertical = 30.dp),
            ) {
                BackButton()
                Spacer(modifier = Modifier.height(65.dp))
                Header()
                Spacer(modifier = Modifier.height(40.dp))
                ToogleButton(
                    toogleState = toogleState,
                    onSelectToogle = onSelectToogle,
                )
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
) {
    // TODO: Logic
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Filé de Frango ao Limão com Cogumelos",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1.0f),
        )

        Spacer(modifier = Modifier.width(15.dp))

        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                modifier = Modifier.size(30.dp),
                contentDescription = null,
            )
        }
    }
}
