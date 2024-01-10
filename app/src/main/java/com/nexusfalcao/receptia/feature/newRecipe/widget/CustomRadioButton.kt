package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.feature.newRecipe.state.RadioUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.RecipeFieldState
import com.nexusfalcao.receptia.ui.theme.Gray400
import com.nexusfalcao.receptia.ui.theme.Olivine

@Composable
fun CustomRadioButton(
    textOption: String,
    radioUiState: RadioUiState,
    addPreference: (RecipeFieldState, String) -> Unit,
) {
    val isSelected = (radioUiState is RadioUiState.Selected) &&
        radioUiState.textOption == textOption

    val roundedCornerShape = RoundedCornerShape(size = 20.dp)
    val textColor = when (isSelected) {
        true -> Color.Black
        false -> Color.Gray
    }
    val selectedModifier = when (isSelected) {
        true -> Modifier.background(
            color = Olivine,
            shape = CircleShape,
        )
        false -> Modifier.border(
            width = 1.dp,
            color = Gray400,
            shape = CircleShape,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Gray400,
                shape = roundedCornerShape,
            )
            .background(
                color = Color.Transparent,
                shape = roundedCornerShape,
            )
            .clip(roundedCornerShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
            ) {
                addPreference(RecipeFieldState.MEAL, textOption)
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = selectedModifier.size(18.dp),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = textOption,
            color = textColor,
        )
    }
}
