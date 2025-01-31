package com.nexusfalcao.description.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.description.R
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.designsystem.extension.scaleHeadlineSmallBy
import com.nexusfalcao.designsystem.preview.PreviewParameterData
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.FilledHeartColor
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.model.Recipe

@Composable
fun Header(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    windowSizeClass: WindowSizeClass,
    onToogleFavorite: () -> Unit = {},
) {
    val cdFavoriteButton = if (recipe.isFavorite) {
        stringResource(R.string.cd_favorited)
    } else {
        stringResource(R.string.cd_unfavorited)
    }
    val iconSize = if (windowSizeClass.hasCompactSize()) {
        30.dp
    } else if (windowSizeClass.hasMediumSize()) {
        (30 * 1.5).dp
    } else {
        (30 * 1.75).dp
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SelectionContainer {
            Text(
                text = recipe.name,
                style = Typography.scaleHeadlineSmallBy(windowSizeClass),
                modifier = Modifier.weight(1.0f),
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.width(15.dp))

        IconButton(onClick = onToogleFavorite) {
            when (recipe.isFavorite) {
                true ->
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        modifier = Modifier.size(iconSize),
                        tint = FilledHeartColor,
                        contentDescription = cdFavoriteButton,
                    )
                false ->
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        modifier = Modifier.size(30.dp),
                        contentDescription = cdFavoriteButton,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
            }
        }
    }
}

@UIModePreview
@Composable
fun HeaderPreviewFavorite()  {
    ReceptIaTheme {
        Header(
            recipe = PreviewParameterData.recipe,
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass()
        )
    }
}

@UIModePreview
@Composable
fun HeaderPreview()  {
    val recipe = PreviewParameterData.recipe
    recipe.isFavorite = false

    ReceptIaTheme {
        Header(
            recipe = recipe,
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass()
        )
    }
}
