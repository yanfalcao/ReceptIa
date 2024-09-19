package com.nexusfalcao.createrecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nexusfalcao.createrecipe.R
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun CustomTextField(
    recipeFieldState: RecipeFieldState,
    checkFieldUiState: CheckFieldUiState,
    onInputIngredient: (RecipeFieldState, String) -> Unit,
) {
    val maxChar = 25
    var textFieldValue by remember { mutableStateOf("") }
    var isEssencialUnfilled =
        CheckFieldUiState.isEssencialFieldUnfilled(
            checkFieldUiState = checkFieldUiState,
            recipeField = recipeFieldState,
        )
    var isUnfilled =
        CheckFieldUiState.isUnfilled(
            checkFieldUiState = checkFieldUiState,
            recipeField = recipeFieldState,
        ) && textFieldValue.isNotEmpty()

    val borderColor = createBorderColor(isEssencialUnfilled)
    val roundedCornerShape = RoundedCornerShape(size = 20.dp)
    val unfilledErrorText = stringResource(id = R.string.error_field)
    val unsentErrorText = stringResource(id = R.string.error_unsent)
    val sendIngredient = {
        onInputIngredient(recipeFieldState, textFieldValue)
        textFieldValue = ""
    }

    Column {
        Row {
            BasicTextField(
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                value = textFieldValue,
                onValueChange = { newText ->
                    if (newText.length <= maxChar) {
                        textFieldValue = newText
                        isEssencialUnfilled = false
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions =
                    KeyboardActions(
                        onSend = {
                            sendIngredient()
                        },
                    ),
                singleLine = true,
                modifier =
                    Modifier.weight(1f)
                        .height(45.dp)
                        .border(width = 1.dp, color = borderColor, shape = roundedCornerShape)
                        .background(color = createBackgroundColor(), shape = roundedCornerShape),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box {
                            if (textFieldValue.isEmpty()) {
                                Text(
                                    text = stringResource(id = R.string.placeholder_ingredient),
                                    color = MaterialTheme.colorScheme.outline,
                                )
                            }
                            innerTextField()
                        }
                    }
                },
            )

            Spacer(modifier = Modifier.width(10.dp))

            SendButtom(
                modifier = Modifier.size(45.dp),
                onClick = sendIngredient,
            )
        }

        if (isUnfilled) {
            hintErrorText(text = unsentErrorText)
        } else if (isEssencialUnfilled) {
            hintErrorText(text = unfilledErrorText)
        }
    }
}

@Composable
private fun hintErrorText(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(start = 10.dp),
    )
}

@Composable
private fun createBackgroundColor(): Color {
    return when (isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.surface
        false -> Color.Transparent
    }
}

@Composable
private fun createBorderColor(isErrorUnfilled: Boolean): Color {
    return if (isErrorUnfilled) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.outline
    }
}

@ThemePreview
@Composable
fun CustomTextFieldPreview() {
    ReceptIaTheme {
        Box(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(50.dp),
        ) {
            CustomTextField(
                recipeFieldState = RecipeFieldState.MEAL,
                checkFieldUiState = CheckFieldUiState.Filled,
                onInputIngredient = { _, _ -> },
            )
        }
    }
}

@ThemePreview
@Composable
fun CustomTextFieldErrorPreview() {
    ReceptIaTheme {
        Box(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(50.dp),
        ) {
            CustomTextField(
                recipeFieldState = RecipeFieldState.FAVORITE,
                checkFieldUiState =
                    CheckFieldUiState.Unfilled(
                        fields = mutableListOf(RecipeFieldState.FAVORITE),
                    ),
                onInputIngredient = { _, _ -> },
            )
        }
    }
}
