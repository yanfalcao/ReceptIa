package com.example.receptia.feature.newRecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.R
import com.example.receptia.feature.newRecipe.state.RadioUiState
import com.example.receptia.ui.theme.Gray
import com.example.receptia.ui.theme.Green
import com.example.receptia.view.widget.TopBarWidget

@Composable
internal fun NewRecipeRoute(
    navController: NavController,
    viewModel: NewRecipeViewModel = viewModel(),
) {
    val radioUiState by viewModel.radioUiState.collectAsStateWithLifecycle()

    NewRecipeScreen(
        radioUiState = radioUiState,
        onSelectOption = viewModel::selectRadio,
        onBackClick = navController::popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewRecipeScreen(
    radioUiState: RadioUiState,
    onSelectOption: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBarWidget(
                title = stringResource(id = R.string.new_recipe_title),
                drawerEnabled = false,
                onBackClick = onBackClick,
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp, top = 20.dp),
        ) {
            RecipeForm(
                radioUiState = radioUiState,
                onSelectOption = onSelectOption,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
                    .padding(top = 15.dp, bottom = 20.dp),
            ) {
                ContinueButtom()
            }
        }
    }
}

@Composable
private fun RecipeForm(
    radioUiState: RadioUiState,
    onSelectOption: (String) -> Unit = {},
) {
    val typesOfDishies = listOf(
        stringResource(R.string.breakfast),
        stringResource(R.string.lunch),
        stringResource(R.string.dinner),
    )
    val ingredientTextList = listOf(
        stringResource(R.string.favorite_ingredients),
        stringResource(R.string.non_favorite_ingredients),
        stringResource(R.string.allergic_ingredients),
        stringResource(R.string.intolerant_ingredients),
    )

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 85.dp),
    ) {
        Text(
            text = stringResource(id = R.string.type_of_dish),
            color = Color.Black,
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            for (type in typesOfDishies) {
                CustomRadioButton(
                    textOption = type,
                    radioUiState = radioUiState,
                    onSelectOption = onSelectOption,
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        for (ingredientText in ingredientTextList) {
            Text(
                text = ingredientText,
                color = Color.Black,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField()

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun ContinueButtom() {
    Button(
        onClick = {
            // TODO: Add start button logic
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Green),
    ) {
        Text(
            text = stringResource(id = R.string.start),
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun CustomTextField() {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    val roundedCornerShape = RoundedCornerShape(size = 20.dp)

    BasicTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 1.dp,
                color = Gray,
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
                    if (text.text.isEmpty()) {
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
}

@Composable
private fun CustomRadioButton(
    textOption: String,
    radioUiState: RadioUiState,
    onSelectOption: (String) -> Unit = {},
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
            color = Green,
            shape = CircleShape,
        )
        false -> Modifier.border(
            width = 1.dp,
            color = Gray,
            shape = CircleShape,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Gray,
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
                onSelectOption(textOption)
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
