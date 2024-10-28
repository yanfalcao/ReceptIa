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
import androidx.compose.ui.unit.dp
import com.nexusfalcao.createrecipe.state.RadioUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun CustomRadioButton(
    textOption: String,
    radioUiState: RadioUiState,
    addPreference: (RecipeFieldState, String) -> Unit,
) {
    val isSelected =
        (radioUiState is RadioUiState.Selected) &&
            (radioUiState.textOption == textOption)

    val radioModifier = createRadioModifier(isSelected)
    val buttonModifier = createButtomModifier(isSelected)
    val textColor = createColorText(isSelected)

    Row(
        modifier =
            buttonModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                ) {
                    addPreference(RecipeFieldState.MEAL, textOption)
                }
                .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = radioModifier.size(18.dp),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = textOption,
            color = textColor,
        )
    }
}

@Composable
private fun createColorText(isSelected: Boolean): Color {
    return when (isSelected) {
        true -> MaterialTheme.colorScheme.onSurface
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
            color = MaterialTheme.colorScheme.outline,
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
            )
        }
    }
}
