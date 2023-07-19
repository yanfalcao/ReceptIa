package com.example.receptia.feature.recipeDescription

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.R
import com.example.receptia.ui.theme.LightOrange
import com.example.receptia.ui.theme.ReceptIaTheme

@Composable
internal fun RecipeDescriptionRoute(
    navController: NavController,
    viewModel: RecipeDescriptionViewModel = viewModel(),
) {
    RecipeDescriptionScreen()
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun RecipeDescriptionScreen() {
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

@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .requiredSize(45.dp),
    ) {
        IconButton(
            onClick = {
                // TODO: Implements
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun Background() {
    val colorStops = arrayOf(
        0.0f to Color.Transparent,
        0.93f to MaterialTheme.colorScheme.background,
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightOrange),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_background_recipe),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.26f)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(colorStops = colorStops),
                ),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(Alignment.BottomCenter)
                .background(color = MaterialTheme.colorScheme.background),
        )
    }
}
