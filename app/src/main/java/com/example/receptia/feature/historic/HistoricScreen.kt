package com.example.receptia.feature.historic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.R
import com.example.receptia.feature.historic.state.RecipeHistoricUiState
import com.example.receptia.feature.historic.widget.GridList
import com.example.receptia.feature.historic.widget.SearchBar
import com.example.receptia.ui.theme.BlackLightTransparent
import com.example.receptia.ui.theme.Green
import com.example.receptia.ui.theme.LightGray
import com.example.receptia.ui.theme.widget.SkeletonLoadingWidget
import com.example.receptia.view.widget.TopBarWidget

@Composable
internal fun HistoricRoute(
    navController: NavController,
    viewModel: HistoricViewModel = viewModel(),
) {
    val historicState by viewModel.recipeHistoricState.collectAsStateWithLifecycle()

    HistoricScreen(
        historicState = historicState,
        onBackClick = navController::popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoricScreen(
    historicState: RecipeHistoricUiState,
    onBackClick: () -> Unit = {},
) {
    val filterTags = listOf(
        stringResource(id = R.string.all),
        stringResource(id = R.string.favorites),
    )

    Scaffold(
        topBar = {
            TopBarWidget(
                title = stringResource(id = R.string.historic_title),
                drawerEnabled = false,
                onBackClick = onBackClick,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Row {
                SearchBar(
                    modifier = Modifier
                        .weight(1.0f)
                        .height(40.dp),
                )

                Spacer(modifier = Modifier.width(15.dp))

                FilterButton(modifier = Modifier.size(40.dp))
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                for (tag in filterTags) {
                    Tag(text = tag)
                }
            }

            when (historicState) {
                RecipeHistoricUiState.Loading -> LoadingRecipeList()
                is RecipeHistoricUiState.Success -> {
                    if (historicState.recipes.isNotEmpty()) {
                        GridList(historicState.recipes)
                    }
                }
            }
        }
    }
}

@Composable
private fun Tag(text: String) {
    // TODO: Implement logic
    Box(
        modifier = Modifier
            .background(
                color = Green,
                shape = RoundedCornerShape(30.dp),
            )
            .padding(vertical = 5.dp, horizontal = 15.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
        )
    }
}

@Composable
private fun FilterButton(modifier: Modifier) {
    Box(
        modifier = modifier
            .background(color = LightGray, shape = CircleShape)
            .shadow(
                elevation = 4.dp,
                spotColor = BlackLightTransparent,
                ambientColor = BlackLightTransparent,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_tune),
            contentDescription = null,
        )
    }
}

@Composable
private fun LoadingRecipeList() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(6) {
            SkeletonLoadingWidget(Modifier.height(145.dp))
        }
    }
}
