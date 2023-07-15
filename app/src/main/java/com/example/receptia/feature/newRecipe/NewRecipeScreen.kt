package com.example.receptia.feature.newRecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.R
import com.example.receptia.feature.newRecipe.state.RadioUiState
import com.example.receptia.ui.theme.Gray
import com.example.receptia.ui.theme.Green
import com.example.receptia.view.widget.NavigationDrawerWidget
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
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewRecipeScreen(
    radioUiState: RadioUiState,
    onSelectOption: (String) -> Unit = {},
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    NavigationDrawerWidget(
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = { TopBarWidget(drawerState) },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 25.dp),
            ) {
                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = stringResource(id = R.string.type_of_dish),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(15.dp))

                Column {
                    CustomRadioButton(
                        textOption = stringResource(R.string.breakfast),
                        radioUiState = radioUiState,
                        onSelectOption = onSelectOption,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomRadioButton(
                        textOption = stringResource(R.string.lunch),
                        radioUiState = radioUiState,
                        onSelectOption = onSelectOption,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    CustomRadioButton(
                        textOption = stringResource(R.string.dinner),
                        radioUiState = radioUiState,
                        onSelectOption = onSelectOption,
                    )
                }
            }
        }
    }
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
        false -> Gray
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
