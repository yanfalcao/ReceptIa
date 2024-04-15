package com.nexusfalcao.description.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.model.Recipe
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.FilledHeartColor
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun Header(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    onToogleFavorite: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.weight(1.0f),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.width(15.dp))

        IconButton(onClick = onToogleFavorite) {
            when (recipe.isFavorite) {
                true -> Icon(
                    imageVector = Icons.Default.Favorite,
                    modifier = Modifier.size(30.dp),
                    tint =  FilledHeartColor,
                    contentDescription = null,
                )
                false -> Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    modifier = Modifier.size(30.dp),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@ThemePreview
@Composable
fun HeaderPreviewFavorite(){
    ReceptIaTheme {
        Header(recipe = PreviewParameterData.recipe)
    }
}

@ThemePreview
@Composable
fun HeaderPreview(){
    val recipe = PreviewParameterData.recipe
    recipe.isFavorite = false

    ReceptIaTheme {
        Header(recipe = recipe)
    }
}
