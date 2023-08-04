package com.example.receptia.feature.newRecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.receptia.R
import com.example.receptia.feature.newRecipe.state.CheckFieldUiState
import com.example.receptia.feature.newRecipe.state.IngredientUiState
import com.example.receptia.feature.newRecipe.state.RadioUiState
import com.example.receptia.feature.newRecipe.state.RecipeFieldState
import com.example.receptia.feature.newRecipe.widget.CustomRadioButton
import com.example.receptia.feature.newRecipe.widget.CustomTextField
import com.example.receptia.feature.newRecipe.widget.FlexBoxLayout
import com.example.receptia.feature.recipeDescription.navigation.navigateToRecipeDescription
import com.example.receptia.ui.theme.Green
import com.example.receptia.view.widget.TopBarWidget

@Composable
internal fun NewRecipeRoute(
    navController: NavController,
    viewModel: NewRecipeViewModel = viewModel(),
) {
    val radioUiState by viewModel.radioUiState.collectAsStateWithLifecycle()
    val favoriteIngredientsState by viewModel.favoriteIngredientsState.collectAsStateWithLifecycle()
    val nonFavoriteIngredientsState by viewModel.nonFavoriteIngredientsState.collectAsStateWithLifecycle()
    val allergicIngredientsState by viewModel.allergicIngredientsState.collectAsStateWithLifecycle()
    val intolerantIngredientsState by viewModel.intolerantIngredientsState.collectAsStateWithLifecycle()
    val checkFieldUiState by viewModel.checkFieldUiState.collectAsStateWithLifecycle()

    NewRecipeScreen(
        radioUiState = radioUiState,
        favoriteIngredientUiState = favoriteIngredientsState,
        nonFavoriteIngredientsState = nonFavoriteIngredientsState,
        allergicIngredientsState = allergicIngredientsState,
        intolerantIngredientsState = intolerantIngredientsState,
        checkFieldUiState = checkFieldUiState,
        checkFields = viewModel::checkFields,
        onSelectOption = viewModel::selectRadio,
        onInputIngredient = viewModel::updateIngredient,
        onRemoveIngredient = viewModel::removeIngredient,
        onBackClick = navController::popBackStack,
        onNavigateToRecipe = navController::navigateToRecipeDescription,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewRecipeScreen(
    radioUiState: RadioUiState,
    favoriteIngredientUiState: IngredientUiState,
    nonFavoriteIngredientsState: IngredientUiState,
    allergicIngredientsState: IngredientUiState,
    intolerantIngredientsState: IngredientUiState,
    checkFieldUiState: CheckFieldUiState,
    checkFields: () -> Unit,
    onSelectOption: (String) -> Unit,
    onInputIngredient: (RecipeFieldState, String) -> Unit,
    onRemoveIngredient: (RecipeFieldState, String) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToRecipe: () -> Unit,
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
                favoriteIngredientUiState = favoriteIngredientUiState,
                nonFavoriteIngredientsState = nonFavoriteIngredientsState,
                allergicIngredientsState = allergicIngredientsState,
                intolerantIngredientsState = intolerantIngredientsState,
                checkFieldUiState = checkFieldUiState,
                onSelectOption = onSelectOption,
                onInputIngredient = onInputIngredient,
                onRemoveIngredient = onRemoveIngredient,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
                    .padding(top = 15.dp, bottom = 20.dp),
            ) {
                ContinueButtom(
                    checkFieldUiState = checkFieldUiState,
                    checkFields = checkFields,
                    onNavigateToRecipe = onNavigateToRecipe,
                )
            }
        }
    }
}

@Composable
private fun RecipeForm(
    radioUiState: RadioUiState,
    favoriteIngredientUiState: IngredientUiState,
    nonFavoriteIngredientsState: IngredientUiState,
    allergicIngredientsState: IngredientUiState,
    intolerantIngredientsState: IngredientUiState,
    checkFieldUiState: CheckFieldUiState,
    onSelectOption: (String) -> Unit = {},
    onInputIngredient: (RecipeFieldState, String) -> Unit,
    onRemoveIngredient: (RecipeFieldState, String) -> Unit,
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
            onSelectOption = onSelectOption,
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
                onInputIngredient = onInputIngredient,
            )

            FlexBoxLayout(
                modifier = Modifier.padding(top = 6.dp),
                ingredientUiState = ingredient.first,
                onRemoveIngredient = onRemoveIngredient,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun RadioField(
    radioUiState: RadioUiState,
    checkFieldUiState: CheckFieldUiState,
    onSelectOption: (String) -> Unit = {},
) {
    val typesOfDishies = listOf(
        stringResource(R.string.breakfast),
        stringResource(R.string.lunch),
        stringResource(R.string.dinner),
    )
    val isError = checkFieldUiState is CheckFieldUiState.Unfilled &&
        checkFieldUiState.field == RecipeFieldState.MEAL

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

    if (isError) {
        Text(
            text = stringResource(id = R.string.error_field),
            color = Color.Red,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 10.dp),
        )
    }
}

@Composable
private fun ContinueButtom(
    checkFieldUiState: CheckFieldUiState,
    checkFields: () -> Unit,
    onNavigateToRecipe: () -> Unit = {},
) {
    Button(
        onClick = {
            when (checkFieldUiState) {
                CheckFieldUiState.Filled -> onNavigateToRecipe()
                else -> checkFields()
            }
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
