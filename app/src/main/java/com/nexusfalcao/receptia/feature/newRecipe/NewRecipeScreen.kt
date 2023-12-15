package com.nexusfalcao.receptia.feature.newRecipe

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.configs.RemoteValues
import com.nexusfalcao.receptia.feature.newRecipe.preview.PreviewParameterData
import com.nexusfalcao.receptia.feature.newRecipe.state.CheckFieldUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.CreateRecipeUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.ErrorUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.IngredientUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.RadioUiState
import com.nexusfalcao.receptia.feature.newRecipe.state.RecipeFieldState
import com.nexusfalcao.receptia.feature.newRecipe.widget.ContinueButtom
import com.nexusfalcao.receptia.feature.newRecipe.widget.CreateRecipeLoading
import com.nexusfalcao.receptia.feature.newRecipe.widget.RecipeForm
import com.nexusfalcao.receptia.feature.recipeDescription.navigation.navigateToRecipeDescription
import com.nexusfalcao.receptia.ui.widget.CustomAlertDialog
import com.nexusfalcao.receptia.ui.widget.CustomSnackbar
import com.nexusfalcao.receptia.view.widget.TopBarWidget

@Composable
internal fun NewRecipeRoute(
    navController: NavController,
    viewModel: NewRecipeViewModel = hiltViewModel(),
) {
    val radioUiState by viewModel.radioUiState.collectAsStateWithLifecycle()
    val favoriteIngredientsState by viewModel.favoriteIngredientsState.collectAsStateWithLifecycle()
    val nonFavoriteIngredientsState by viewModel.nonFavoriteIngredientsState.collectAsStateWithLifecycle()
    val allergicIngredientsState by viewModel.allergicIngredientsState.collectAsStateWithLifecycle()
    val intolerantIngredientsState by viewModel.intolerantIngredientsState.collectAsStateWithLifecycle()
    val checkFieldUiState by viewModel.checkFieldUiState.collectAsStateWithLifecycle()
    val createRecipeUiState by viewModel.createRecipeUiState.collectAsStateWithLifecycle()
    val isMaxIngredientLimit by viewModel.isMaxIngredientsLimit.collectAsStateWithLifecycle()

    NewRecipeScreen(
        radioUiState = radioUiState,
        favoriteIngredientUiState = favoriteIngredientsState,
        nonFavoriteIngredientsState = nonFavoriteIngredientsState,
        allergicIngredientsState = allergicIngredientsState,
        intolerantIngredientsState = intolerantIngredientsState,
        checkFieldUiState = checkFieldUiState,
        createRecipeUiState = createRecipeUiState,
        isMaxIngredientLimit = isMaxIngredientLimit,
        createRecipe = viewModel::createRecipe,
        addPreference = viewModel::addPreference,
        removePreference = viewModel::removePreference,
        onBackClick = navController::popBackStack,
        onNavigateToRecipe = navController::navigateToRecipeDescription,
        cleanCreateRecipeUiState = viewModel::cleanCreateRecipeUiState,
    )
}

@Composable
private fun NewRecipeScreen(
    radioUiState: RadioUiState,
    favoriteIngredientUiState: IngredientUiState,
    nonFavoriteIngredientsState: IngredientUiState,
    allergicIngredientsState: IngredientUiState,
    intolerantIngredientsState: IngredientUiState,
    checkFieldUiState: CheckFieldUiState,
    createRecipeUiState: CreateRecipeUiState,
    isMaxIngredientLimit: ErrorUiState,
    createRecipe: () -> Unit,
    addPreference: (RecipeFieldState, String) -> Unit,
    removePreference: (RecipeFieldState, String) -> Unit,
    onBackClick: () -> Unit,
    onNavigateToRecipe: (String) -> Unit,
    cleanCreateRecipeUiState: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var openDialog by remember { mutableStateOf(false) }
    val limitErrorToast = stringResource(id = R.string.error_max_ingredient)
    val createRecipeErrorToast = stringResource(id = R.string.error_create_recipe)
    val context = LocalContext.current
    val onContinueClick = {
        if(RemoteValues.VALUE_CHATGPT_API_ENABLED) {
            createRecipe()
        } else {
            openDialog = true
        }
    }


    LaunchedEffect(isMaxIngredientLimit, createRecipeUiState) {
        if (isMaxIngredientLimit is ErrorUiState.IngredientMaxLimit) {
            Toast.makeText(context, limitErrorToast, Toast.LENGTH_SHORT).show()
        }
        when (createRecipeUiState) {
            CreateRecipeUiState.Error -> {
                snackbarHostState.showSnackbar(createRecipeErrorToast)
            }
            is CreateRecipeUiState.Success -> {
                cleanCreateRecipeUiState()
                onNavigateToRecipe(createRecipeUiState.recipeId)
            }
            else -> {}
        }
    }

    Box {
        Scaffold(
            snackbarHost = {
                CustomSnackbar.Alert(hostState = snackbarHostState)
            },
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
                    addPreference = addPreference,
                    removePreference = removePreference,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .align(Alignment.BottomCenter)
                        .padding(top = 15.dp, bottom = 20.dp),
                ) {
                    ContinueButtom(createRecipe = onContinueClick)
                }
            }
        }

        if (createRecipeUiState is CreateRecipeUiState.Loading) {
            CreateRecipeLoading()
        }
    }

    if(openDialog) {
        CustomAlertDialog(
            title = stringResource(id = R.string.alert),
            description = stringResource(id = R.string.alert_description_api_limit),
        ) {
            openDialog = false
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun NewRecipeScreenPreview() {
    NewRecipeScreen(
        radioUiState = RadioUiState.Selected("Jantar"),
        favoriteIngredientUiState = IngredientUiState(
            ingredients = PreviewParameterData.ingredients,
            state = RecipeFieldState.FAVORITE,
        ),
        nonFavoriteIngredientsState = IngredientUiState(
            state = RecipeFieldState.FAVORITE,
        ),
        allergicIngredientsState = IngredientUiState(
            state = RecipeFieldState.ALLERGIC,
        ),
        intolerantIngredientsState = IngredientUiState(
            state = RecipeFieldState.INTOLERANT,
        ),
        checkFieldUiState = CheckFieldUiState.None,
        createRecipeUiState = CreateRecipeUiState.None,
        isMaxIngredientLimit = ErrorUiState.None,
        createRecipe = {},
        addPreference = { _, _ -> },
        removePreference = { _, _ -> },
        onBackClick = {},
        onNavigateToRecipe = { _ -> },
        cleanCreateRecipeUiState = {},
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun LoadingStatePreview() {
    NewRecipeScreen(
        radioUiState = RadioUiState.Selected("Jantar"),
        favoriteIngredientUiState = IngredientUiState(
            ingredients = PreviewParameterData.ingredients,
            state = RecipeFieldState.FAVORITE,
        ),
        nonFavoriteIngredientsState = IngredientUiState(
            state = RecipeFieldState.FAVORITE,
        ),
        allergicIngredientsState = IngredientUiState(
            state = RecipeFieldState.ALLERGIC,
        ),
        intolerantIngredientsState = IngredientUiState(
            state = RecipeFieldState.INTOLERANT,
        ),
        checkFieldUiState = CheckFieldUiState.None,
        createRecipeUiState = CreateRecipeUiState.Loading,
        isMaxIngredientLimit = ErrorUiState.None,
        createRecipe = {},
        addPreference = { _, _ -> },
        removePreference = { _, _ -> },
        onBackClick = {},
        onNavigateToRecipe = { _ -> },
        cleanCreateRecipeUiState = {},
    )
}
