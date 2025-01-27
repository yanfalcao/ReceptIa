package com.nexusfalcao.createrecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.createrecipe.R
import com.nexusfalcao.createrecipe.state.RadioUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.designsystem.extension.scaleBodyMediumBy
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun CustomRadioButton(
    textOption: String,
    radioUiState: RadioUiState,
    addPreference: (RecipeFieldState, String) -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    val isSelected =
        (radioUiState is RadioUiState.Selected) &&
            (radioUiState.textOption == textOption)

    val radioDescription = if (isSelected) {
        stringResource(R.string.selected)
    } else {
        stringResource(R.string.unselected)
    }
    val radioModifier = createRadioModifier(isSelected)
    val buttonModifier = createButtomModifier(isSelected)
    val textColor = MaterialTheme.colorScheme.onSurface
    val radioSize = if (windowSizeClass.hasCompactSize()) {
        18.dp
    } else if (windowSizeClass.hasMediumSize()) {
        (18 * 1.5).dp
    } else {
        (18 * 1.75).dp
    }

    Row(
        modifier =
            buttonModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                ) {
                    addPreference(RecipeFieldState.MEAL, textOption)
                }
                .semantics(mergeDescendants = true) {
                    contentDescription = radioDescription
                }
                .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = radioModifier.size(radioSize),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = textOption,
            color = textColor,
            style = Typography.scaleBodyMediumBy(windowSizeClass),
        )
    }
}

@Composable
private fun createColorBorder(isSelected: Boolean): Color {
    return when (isSelected) {
        true -> MaterialTheme.colorScheme.primary
        false -> MaterialTheme.colorScheme.outline
    }
}

@Composable
private fun createButtomModifier(isSelected: Boolean): Modifier {
    val roundedCornerShape = RoundedCornerShape(size = 20.dp)
    val backgroundColor =
        when {
            !isSystemInDarkTheme() -> Color.Transparent
            isSelected -> MaterialTheme.colorScheme.surfaceVariant
            !isSelected -> MaterialTheme.colorScheme.surface
            else -> Color.Transparent
        }

    return Modifier
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = createColorBorder(isSelected),
            shape = roundedCornerShape,
        )
        .background(
            color = backgroundColor,
            shape = roundedCornerShape,
        )
        .clip(roundedCornerShape)
}

@Composable
private fun createRadioModifier(isSelected: Boolean): Modifier {
    return when (isSelected) {
        true ->
            Modifier.background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            )
        false ->
            Modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape,
            )
    }
}

@UIModePreview
@Composable
fun UnselectedRadioButtonPreview() {
    ReceptIaTheme {
        Box(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(50.dp),
        ) {
            CustomRadioButton(
                textOption = "Café da Manhã",
                radioUiState = RadioUiState.Unselected,
                addPreference = { _, _ -> },
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }
    }
}

@UIModePreview
@Composable
fun SelectedRadioButtonPreview() {
    ReceptIaTheme {
        Box(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(50.dp),
        ) {
            CustomRadioButton(
                textOption = "Café da Manhã",
                radioUiState = RadioUiState.Selected(textOption = "Café da Manhã"),
                addPreference = { _, _ -> },
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }
    }
}
