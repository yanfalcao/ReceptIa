package com.nexusfalcao.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.designsystem.extension.scaleBodyLargeBy
import com.nexusfalcao.designsystem.extension.scaleTitleMediumBy
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.DifficultIcon
import com.nexusfalcao.home.R
import com.nexusfalcao.home.preview.RecipesPreviewParameterProvider
import com.nexusfalcao.model.Recipe

@Composable
fun RecipeList(
    recipes: List<Recipe>,
    navigateToDescription: (String) -> Unit,
    onRemove: (Recipe) -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    val spaceBetweenItems = if (windowSizeClass.hasCompactSize()) {
        5.dp
    } else if (windowSizeClass.hasMediumSize()) {
        (5 * 1.5).dp
    } else {
        (5 * 1.75).dp
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(spaceBetweenItems),
    ) {
        for(item in recipes) {
            RecipeListTile(
                recipe = item,
                navigateToDescription = navigateToDescription,
                onRemove = onRemove,
                windowSizeClass = windowSizeClass,
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
private fun RecipeListTile(
    recipe: Recipe,
    navigateToDescription: (String) -> Unit,
    onRemove: (Recipe) -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    val colorScheme = MaterialTheme.colorScheme
    val bookmarkColor =
        if (recipe.isFavorite) {
            colorScheme.primary
        } else {
            colorScheme.surfaceTint
        }
    val colorFilter =
        when (isSystemInDarkTheme()) {
            true -> ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
            false -> null
        }
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onRemove(recipe)
            }
            // Reset item when toggling done status
            it != SwipeToDismissBoxValue.EndToStart
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier.fillMaxWidth(),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color.Red,
                                shape = RoundedCornerShape(size = 15.dp)
                            )
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                else -> {}
            }
        }
    ) {
        SelectionContainer {
            Column(
                modifier =
                Modifier
                    .background(
                        color = colorScheme.surface,
                        shape = RoundedCornerShape(size = 15.dp),
                    )
                    .clickable {
                        navigateToDescription(recipe.id)
                    }
                    .padding(start = 15.dp, end = 25.dp)
                    .fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_bookmark),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = bookmarkColor),
                    )

                    Text(
                        text = recipe.name,
                        color = colorScheme.onSurface,
                        style = Typography.scaleTitleMediumBy(windowSizeClass),
                        modifier =
                        Modifier
                            .padding(start = 40.dp, top = 12.dp),
                    )
                }

                Row(
                    modifier =
                    Modifier
                        .padding(top = 15.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_clock),
                        contentDescription = null,
                        colorFilter = colorFilter,
                    )

                    Text(
                        text = recipe.recipeDetails.preparationTime,
                        color = colorScheme.onSurface,
                        style = Typography.scaleBodyLargeBy(windowSizeClass),
                        modifier = Modifier.padding(start = 8.dp),
                    )

                    DifficultIcon(
                        recipeDifficult = recipe.recipeDetails.recipeDifficult,
                        modifier = Modifier.padding(start = 50.dp),
                    )

                    Text(
                        text = recipe.recipeDetails.difficult,
                        color = colorScheme.onSurface,
                        style = Typography.scaleBodyLargeBy(windowSizeClass),
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    }
}

@FontSizeAcessibilityPreview
@UIModePreview
@Composable
private fun RecipeListPreview(
    @PreviewParameter(RecipesPreviewParameterProvider::class)
    recipes: List<Recipe>,
) {
    ReceptIaTheme {
        RecipeList(
            recipes = recipes,
            navigateToDescription = {},
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass()
        )
    }
}
