package com.example.receptia.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.receptia.R
import com.example.receptia.model.Recipe
import com.example.receptia.ui.theme.Green
import com.example.receptia.ui.theme.LightGray
import com.example.receptia.ui.theme.LightGreen
import com.example.receptia.ui.theme.widget.SkeletonLoadingWidget
import com.example.receptia.view.widget.NavigationDrawerWidget
import com.example.receptia.view.widget.TopBarWidget

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = viewModel(),
) {
    val feedState by viewModel.feedState.collectAsStateWithLifecycle()

    HomeScreen(feedState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    feedState: RecipeFeedUiState,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawerWidget(
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = { TopBarWidget(drawerState) },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 25.dp),
            ) {
                Banner()

                Text(
                    text = stringResource(id = R.string.last_recipes_title),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(25.dp))

                when (feedState) {
                    RecipeFeedUiState.Loading ->
                        LoadingRecipeList()
                    is RecipeFeedUiState.Success -> {
                        if (feedState.recipes.isNotEmpty()) {
                            RecipeList(feedState.recipes)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Banner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp)
            .background(
                color = LightGreen,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(
                start = 15.dp,
                end = 15.dp,
                top = 20.dp,
                bottom = 10.dp,
            ),
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_snacks),
            contentDescription = null,
            modifier = Modifier
                .height(122.dp)
                .width(113.dp).align(Alignment.BottomEnd),
        )

        Column {
            Text(
                text = stringResource(id = R.string.banner_title),
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(230.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // TODO: Add start button logic
                },
                colors = ButtonDefaults.buttonColors(containerColor = Green),
            ) {
                Text(
                    text = stringResource(id = R.string.start),
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun RecipeList(recipes: List<Recipe>) {
    LazyColumn {
        items(recipes) {
            RecipeListTile(recipe = it)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
private fun RecipeListTile(recipe: Recipe) {
    val bookmarkIcon = if (recipe.isFavorite) {
        R.drawable.ic_bookmark_green
    } else {
        R.drawable.ic_bookmark
    }

    // TODO: Add ease icon logic
    val easeIcon = R.drawable.ic_smile

    Box(
        modifier = Modifier
            .background(
                color = LightGray,
                shape = RoundedCornerShape(size = 15.dp),
            )
            .padding(start = 15.dp, end = 25.dp)
            .fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = bookmarkIcon),
            contentDescription = null,
        )

        Text(
            text = recipe.name,
            color = Color.Black,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(start = 40.dp, top = 12.dp),
        )

        Row(
            modifier = Modifier
                .padding(top = 75.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = null,
            )

            Text(
                text = recipe.prepTime,
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp),
            )

            Image(
                painter = painterResource(id = easeIcon),
                contentDescription = null,
                modifier = Modifier.padding(start = 50.dp),
            )

            Text(
                text = recipe.easeRecipe,
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp),
            )
        }
    }
}

@Composable
private fun LoadingRecipeList() {
    Column {
        repeat(4) {
            SkeletonLoadingWidget()
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}
