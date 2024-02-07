package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.feature.newRecipe.state.CheckFieldUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.IngredientUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.RecipeFieldState
import com.nexusfalcao.receptia.ui.preview.ThemePreview
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme

@Composable
fun CustomTextField(
    ingredientUiState: IngredientUiState,
    checkFieldUiState: CheckFieldUiState,
    onInputIngredient: (RecipeFieldState, String) -> Unit,
) {
    val maxChar = 25
    var textFieldValue by remember { mutableStateOf("") }
    var isErrorLimitChar by remember { mutableStateOf(false) }
    var isErrorUnfilled = checkFieldUiState is CheckFieldUiState.Unfilled &&
        checkFieldUiState.equalsField(ingredientUiState.state)

    val borderColor = createBorderColor(isErrorUnfilled, isErrorLimitChar)
    val backgroundColor = createBackgroundColor()
    val roundedCornerShape = RoundedCornerShape(size = 20.dp)
    val errorText = createErrorText(isErrorUnfilled)

    Column {
        BasicTextField(
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            value = textFieldValue,
            onValueChange = { newText ->
                if (newText.length <= maxChar) {
                    textFieldValue = newText
                    isErrorUnfilled = false
                    isErrorLimitChar = false
                } else {
                    isErrorLimitChar = true
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(
                onSend = {
                    onInputIngredient(ingredientUiState.state, textFieldValue)
                    textFieldValue = ""
                    isErrorLimitChar = false
                },
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .border(width = 1.dp, color = borderColor, shape = roundedCornerShape)
                .background(color = backgroundColor, shape = roundedCornerShape),
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
        if (isErrorUnfilled || isErrorLimitChar) {
            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
    }
}

@Composable
private fun createErrorText(isErrorUnfilled: Boolean): String {
    return if (isErrorUnfilled) {
        stringResource(id = R.string.error_field)
    } else {
        stringResource(id = R.string.error_max_char)
    }
}

@Composable
private fun createBackgroundColor(): Color {
    return when(isSystemInDarkTheme()) {
        true -> MaterialTheme.colorScheme.surface
        false -> Color.Transparent
    }
}

@Composable
private fun createBorderColor(isErrorUnfilled: Boolean, isErrorLimitChar: Boolean): Color {
    return if (isErrorUnfilled || isErrorLimitChar) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.outline
    }
}

@ThemePreview
@Composable
fun CustomTextFieldPreview(){
    ReceptIaTheme {
        Box (
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(50.dp)
        ){
            CustomTextField(
                ingredientUiState = IngredientUiState(
                    ingredients = listOf("Macarrão", "Cogumelo"),
                    state = RecipeFieldState.MEAL
                ),
                checkFieldUiState = CheckFieldUiState.Filled,
                onInputIngredient = {_, _ ->},
            )
        }
    }
}

@ThemePreview
@Composable
fun CustomTextFieldErrorPreview(){
    ReceptIaTheme {
        Box (
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(50.dp)
        ){
            CustomTextField(
                ingredientUiState = IngredientUiState(
                    ingredients = listOf(),
                    state = RecipeFieldState.FAVORITE
                ),
                checkFieldUiState = CheckFieldUiState.Unfilled(
                    fields = mutableListOf(RecipeFieldState.FAVORITE)
                ),
                onInputIngredient = {_, _ ->},
            )
        }
    }
}
