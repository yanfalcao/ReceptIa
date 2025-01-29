package com.nexusfalcao.description.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.description.R
import com.nexusfalcao.description.state.ToogleRecipeState
import com.nexusfalcao.designsystem.extension.scaleTitleMediumBy
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun ToogleButton(
    modifier: Modifier = Modifier,
    toogleState: ToogleRecipeState,
    onSelectToogle: () -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    val cornerShape = RoundedCornerShape(30.dp)
    val isSelectedDetails = toogleState is ToogleRecipeState.DetailsSelected
    val cdToogleRecipeText = if (isSelectedDetails) {
        stringResource(R.string.cd_details_selected)
    } else {
        stringResource(R.string.cd_recipe_selected)
    }

    Box(
        modifier =
            modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = cornerShape,
                )
                .fillMaxWidth()
                .clip(cornerShape)
                .clickable { onSelectToogle() },
    ) {
        val modifierToogle =
            Modifier
                .fillMaxWidth(0.5f)

        Box(
            modifier =
                modifierToogle
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = cornerShape,
                    )
                    .align(if (!isSelectedDetails) Alignment.TopStart else Alignment.TopEnd)
                    .semantics {
                        contentDescription = cdToogleRecipeText
                    },
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center)
                    .padding(vertical = 6.dp)
                    .clearAndSetSemantics {  },
                text =
                    stringResource(
                        id = if (!isSelectedDetails) R.string.details else R.string.recipe,
                    ),
                color = MaterialTheme.colorScheme.onSurface,
                style = Typography.scaleTitleMediumBy(windowSizeClass),
            )
        }

        Box(
            modifier =
                modifierToogle
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = cornerShape,
                    )
                    .align(if (isSelectedDetails) Alignment.TopStart else Alignment.TopEnd),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center)
                    .padding(vertical = 6.dp)
                    .clearAndSetSemantics {  },
                text =
                    stringResource(
                        id = if (isSelectedDetails) R.string.details else R.string.recipe,
                    ),
                color = MaterialTheme.colorScheme.onPrimary,
                style = Typography.scaleTitleMediumBy(windowSizeClass),
            )
        }
    }
}

@UIModePreview
@Composable
private fun ToogleButtonDetailsPreview() {
    ReceptIaTheme {
        ToogleButton(
            toogleState = ToogleRecipeState.DetailsSelected,
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}

@UIModePreview
@Composable
private fun ToogleButtonRecipePreview() {
    ReceptIaTheme {
        ToogleButton(
            toogleState = ToogleRecipeState.RecipeSelected,
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}
