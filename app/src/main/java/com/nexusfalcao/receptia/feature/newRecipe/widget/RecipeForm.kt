package com.nexusfalcao.receptia.feature.newRecipe.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.feature.newRecipe.state.CheckFieldUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.IngredientUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.RadioUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.RecipeFieldState

@Composable
fun RecipeForm(
    radioUiState: RadioUiState,
    favoriteIngredientUiState: IngredientUiState,
    nonFavoriteIngredientsState: IngredientUiState,
    allergicIngredientsState: IngredientUiState,
    intolerantIngredientsState: IngredientUiState,
    checkFieldUiState: CheckFieldUiState,
    addPreference: (RecipeFieldState, String) -> Unit,
    removePreference: (RecipeFieldState, String) -> Unit,
) {
    val ingredientTypeList = listOf(
        Pair(favoriteIngredientUiState, stringResource(R.string.favorite_ingredients)),
        Pair(nonFavoriteIngredientsState, stringResource(R.string.non_favorite_ingredients)),
        Pair(allergicIngredientsState, stringResource(R.string.allergic_ingredients)),
        Pair(intolerantIngredientsState, stringResource(R.string.intolerant_ingredients)),
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

        RadioField(
            checkFieldUiState = checkFieldUiState,
            radioUiState = radioUiState,
            addPreference = addPreference,
        )

        Spacer(modifier = Modifier.height(20.dp))

        for (ingredient in ingredientTypeList) {
            Text(
                text = ingredient.second,
                color = Color.Black,
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                ingredientUiState = ingredient.first,
                checkFieldUiState = checkFieldUiState,
                onInputIngredient = addPreference,
            )

            FlexBoxLayout(
                modifier = Modifier.padding(top = 6.dp),
                ingredientUiState = ingredient.first,
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
) {
    val typesOfDishies = listOf(
        stringResource(R.string.breakfast),
        stringResource(R.string.lunch),
        stringResource(R.string.dinner),
    )
    val isError = checkFieldUiState is CheckFieldUiState.Unfilled &&
        checkFieldUiState.equalsField(RecipeFieldState.MEAL)

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        for (type in typesOfDishies) {
            CustomRadioButton(
                textOption = type,
                radioUiState = radioUiState,
                addPreference = addPreference,
            )
        }
    }

    if (isError) {
        Text(
            text = stringResource(id = R.string.error_field),
            color = Color.Red,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 10.dp),
        )
    }
}
