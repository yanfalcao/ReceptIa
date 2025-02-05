package com.nexusfalcao.createrecipe.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.nexusfalcao.createrecipe.R
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.FieldsUiState
import com.nexusfalcao.createrecipe.state.RadioUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.designsystem.extension.scaleLabelMediumBy
import com.nexusfalcao.designsystem.extension.scaleTitleMediumBy
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme

@Composable
fun RecipeForm(
    fieldsUiState: FieldsUiState,
    checkFieldUiState: CheckFieldUiState,
    addPreference: (RecipeFieldState, String) -> Unit,
    removePreference: (RecipeFieldState, String) -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    val ingredientTypeList =
        listOf(
            Pair(RecipeFieldState.FAVORITE, stringResource(R.string.favorite_ingredients)),
            Pair(RecipeFieldState.NON_FAVORITE, stringResource(R.string.non_favorite_ingredients)),
            Pair(RecipeFieldState.ALLERGIC, stringResource(R.string.allergic_ingredients)),
            Pair(RecipeFieldState.INTOLERANT, stringResource(R.string.intolerant_ingredients)),
        )
    val formFraction = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 1.0f
        WindowWidthSizeClass.MEDIUM -> 0.8f
        else -> 0.6f
    }

    Column(
        modifier =
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 85.dp)
                .fillMaxWidth(fraction = formFraction),
    ) {
        Text(
            text = stringResource(id = R.string.type_of_dish),
            color = MaterialTheme.colorScheme.onBackground,
            style = Typography.scaleTitleMediumBy(windowSizeClass),
        )

        Spacer(modifier = Modifier.height(15.dp))

        RadioField(
            checkFieldUiState = checkFieldUiState,
            radioUiState = fieldsUiState.meal,
            addPreference = addPreference,
            windowSizeClass = windowSizeClass,
        )

        Spacer(modifier = Modifier.height(20.dp))

        for (ingredient in ingredientTypeList) {
            Text(
                text = ingredient.second,
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.scaleTitleMediumBy(windowSizeClass),
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                recipeFieldState = ingredient.first,
                checkFieldUiState = checkFieldUiState,
                onInputIngredient = addPreference,
                windowSizeClass = windowSizeClass,
            )

            FlexBoxLayout(
                modifier = Modifier.padding(top = 6.dp),
                fieldsUiState = fieldsUiState,
                recipeFieldState = ingredient.first,
                onRemoveIngredient = removePreference,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun RadioField(
    radioUiState: RadioUiState,
    checkFieldUiState: CheckFieldUiState,
    addPreference: (RecipeFieldState, String) -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    val typesOfDishies =
        listOf(
            stringResource(R.string.breakfast),
            stringResource(R.string.lunch),
            stringResource(R.string.dinner),
        )
    val isError =
        CheckFieldUiState.isUnfilled(
            checkFieldUiState = checkFieldUiState,
            recipeField = RecipeFieldState.MEAL,
        )

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        for (type in typesOfDishies) {
            CustomRadioButton(
                textOption = type,
                radioUiState = radioUiState,
                addPreference = addPreference,
                windowSizeClass = windowSizeClass,
            )
        }
    }

    if (isError) {
        Text(
            text = stringResource(id = R.string.error_field),
            color = MaterialTheme.colorScheme.error,
            style = Typography.scaleLabelMediumBy(windowSizeClass),
            modifier = Modifier.padding(start = 10.dp),
        )
    }
}

@UIModePreview
@Composable
fun RecipeFormPreview() {
    val fieldsUiState =
        FieldsUiState(
            favoriteIngredients = arrayListOf("Macarrão", "Cogumelo"),
        )
    fieldsUiState.addField(RecipeFieldState.MEAL, "Almoço")

    ReceptIaTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            RecipeForm(
                fieldsUiState = fieldsUiState,
                checkFieldUiState = CheckFieldUiState.Filled,
                addPreference = { _, _ -> },
                removePreference = { _, _ -> },
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }
    }
}

@UIModePreview
@Composable
fun RecipeFormErrorPreview() {
    ReceptIaTheme {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            RecipeForm(
                fieldsUiState = FieldsUiState(),
                checkFieldUiState =
                    CheckFieldUiState.Unfilled(
                        mutableListOf(RecipeFieldState.MEAL, RecipeFieldState.FAVORITE),
                    ),
                addPreference = { _, _ -> },
                removePreference = { _, _ -> },
                windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
            )
        }
    }
}
