package com.example.receptia.feature.historic.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.model.Recipe
import com.example.receptia.ui.theme.LightGray

@Composable
fun GridList(recipes: List<Recipe>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(recipes) {
            GridTile(it)
        }
    }
}

@Composable
private fun GridTile(recipe: Recipe) {
    // TODO: Add ease icon logic
    val easeIcon = R.drawable.ic_smile

    Column(
        modifier = Modifier
            .background(
                color = LightGray,
                shape = RoundedCornerShape(15.dp),
            )
            .fillMaxWidth()
            .height(145.dp)
            .padding(horizontal = 10.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = recipe.name,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Black,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1.0f))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )

            Text(
                text = recipe.prepTime,
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 5.dp),
            )

            Spacer(modifier = Modifier.weight(1.0f))

            Image(
                painter = painterResource(id = easeIcon),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )

            Text(
                text = recipe.easeRecipe,
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 5.dp),
            )
        }
    }
}