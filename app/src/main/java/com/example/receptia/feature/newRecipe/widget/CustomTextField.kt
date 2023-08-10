package com.example.receptia.feature.newRecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.feature.newRecipe.state.CheckFieldUiState
import com.example.receptia.feature.newRecipe.state.IngredientUiState
import com.example.receptia.feature.newRecipe.state.RecipeFieldState
import com.example.receptia.ui.theme.Gray

@Composable
fun CustomTextField(
    ingredientUiState: IngredientUiState,
    checkFieldUiState: CheckFieldUiState,
    onInputIngredient: (RecipeFieldState, String) -> Unit,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val roundedCornerShape = RoundedCornerShape(size = 20.dp)
    var isError = checkFieldUiState is CheckFieldUiState.Unfilled &&
        checkFieldUiState.equalsField(ingredientUiState.state)

    val borderColor = if (isError) Color.Red else Gray

    Column {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { newText ->
                textFieldValue = newText
                isError = false
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    onInputIngredient(ingredientUiState.state, textFieldValue.text)
                    textFieldValue = TextFieldValue("")
                },
            ),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = roundedCornerShape,
                )
                .background(
                    color = Color.Transparent,
                    shape = roundedCornerShape,
                ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box {
                        if (textFieldValue.text.isEmpty()) {
                            Text(
                                text = stringResource(id = R.string.placeholder_ingredient),
                                color = Color.Gray,
                            )
                        }
                        innerTextField()
                    }
                }
            },
        )
        if (isError) {
            Text(
                text = stringResource(id = R.string.error_field),
                color = Color.Red,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
    }
}
