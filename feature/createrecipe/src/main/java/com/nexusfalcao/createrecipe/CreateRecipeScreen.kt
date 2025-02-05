package com.nexusfalcao.createrecipe

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.core.layout.WindowSizeClass
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nexusfalcao.createrecipe.preview.PreviewParameterData
import com.nexusfalcao.createrecipe.state.CheckFieldUiState
import com.nexusfalcao.createrecipe.state.CreateRecipeUiState
import com.nexusfalcao.createrecipe.state.ErrorUiState
import com.nexusfalcao.createrecipe.state.FieldsUiState
import com.nexusfalcao.createrecipe.state.RecipeFieldState
import com.nexusfalcao.createrecipe.widget.ContinueButtom
import com.nexusfalcao.createrecipe.widget.CreateRecipeLoading
import com.nexusfalcao.createrecipe.widget.RecipeForm
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.UIModeBakgroundPreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.CustomAlertDialog
import com.nexusfalcao.designsystem.widget.CustomSnackbar
import com.nexusfalcao.designsystem.widget.TopBarWidget
import kotlinx.coroutines.launch

@Composable
internal fun CreateRecipeRoute(
    isChatGptApiEnabled: Boolean,
    chatGptApiModel: String,
    onNavigateToRecipeDescription: (String) -> Unit,
    popBackStack: () -> Unit,
    isNetworkConnected: () -> Boolean,
    viewModel: CreateRecipeViewModel = hiltViewModel<CreateRecipeViewModel, CreateRecipeViewModel.Factory>(
        creationCallback = { factory ->
            factory.create(FirebaseCrashlytics.getInstance())
        }
    ),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val fieldsUiState by viewModel.fieldsUiState.collectAsStateWithLifecycle()
    val checkFieldUiState by viewModel.checkFieldUiState.collectAsStateWithLifecycle()
    val createRecipeUiState by viewModel.createRecipeUiState.collectAsStateWithLifecycle()
    val errorUiState by viewModel.errorUiState.collectAsStateWithLifecycle()

    CreateRecipeScreen(
        fieldsUiState = fieldsUiState,
        checkFieldUiState = checkFieldUiState,
        createRecipeUiState = createRecipeUiState,
        errorUiState = errorUiState,
        createRecipe = { viewModel.createRecipe(chatGptApiModel) },
        addPreference = viewModel::addPreference,
        removePreference = viewModel::removePreference,
        onBackClick = popBackStack,
        onNavigateToRecipe = onNavigateToRecipeDescription,
        cleanCreateRecipeUiState = viewModel::cleanCreateRecipeUiState,
        isChatGptApiEnabled = isChatGptApiEnabled,
        isNetworkConnected = isNetworkConnected,
        windowSizeClass = windowSizeClass,
    )
}

@Composable
fun CreateRecipeScreen(
    fieldsUiState: FieldsUiState,
    checkFieldUiState: CheckFieldUiState,
    createRecipeUiState: CreateRecipeUiState,
    errorUiState: ErrorUiState,
    createRecipe: () -> Unit,
    cleanCreateRecipeUiState: () -> Unit,
    addPreference: (RecipeFieldState, String) -> Unit,
    removePreference: (RecipeFieldState, String) -> Unit,
    isChatGptApiEnabled: Boolean,
    isNetworkConnected: () -> Boolean,
    onBackClick: () -> Unit,
    onNavigateToRecipe: (String) -> Unit,
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val snackbarHostState = remember { SnackbarHostState() }
    var openDialog by remember { mutableStateOf(false) }
    val stringErrorMaxChar = stringResource(id = R.string.error_max_char)
    val stringErrorCreateRecipe = stringResource(id = R.string.error_create_recipe)
    val networkErrorMessage = stringResource(id = R.string.network_error)
    val context = LocalContext.current
    val onContinueClick: () -> Unit = {
        if (!isNetworkConnected()) {
            lifecycleScope.launch {
                snackbarHostState.showSnackbar(message = networkErrorMessage)
            }
        } else {
            if (isChatGptApiEnabled) {
                createRecipe()
            } else {
                openDialog = true
            }
        }
    }

    LaunchedEffect(errorUiState, createRecipeUiState) {
        if (errorUiState is ErrorUiState.IngredientMaxLimit) {
            Toast.makeText(context, stringErrorMaxChar, Toast.LENGTH_SHORT).show()
        }
        when (createRecipeUiState) {
            CreateRecipeUiState.Error -> {
                snackbarHostState.showSnackbar(stringErrorCreateRecipe)
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
                    windowSizeClass = windowSizeClass,
                )
            },
        ) { padding ->
            Column(
                modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(start = 25.dp, end = 25.dp, top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.weight(weight = 1.0f),
                ) {
                    RecipeForm(
                        fieldsUiState = fieldsUiState,
                        checkFieldUiState = checkFieldUiState,
                        addPreference = addPreference,
                        removePreference = removePreference,
                        windowSizeClass = windowSizeClass,
                    )
                }

                ContinueButtom(
                    windowSizeClass = windowSizeClass,
                    createRecipe = onContinueClick,
                    modifier =
                    Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .align(Alignment.CenterHorizontally),
                )
            }
        }

        if (createRecipeUiState is CreateRecipeUiState.Loading) {
            CreateRecipeLoading()
        }
    }

    if (openDialog) {
        CustomAlertDialog(
            title = stringResource(id = R.string.alert),
            description = stringResource(id = R.string.alert_description_api_limit),
        ) {
            openDialog = false
        }
    }
}

@FontSizeAcessibilityPreview
@UIModeBakgroundPreview
@WindowSizePreview
@Composable
private fun NewRecipePreview() {
    val fieldsUiState =
        FieldsUiState(
            favoriteIngredients = PreviewParameterData.ingredients,
        )
    fieldsUiState.addField(RecipeFieldState.MEAL, "Jantar")

    ReceptIaTheme {
        CreateRecipeScreen(
            fieldsUiState = fieldsUiState,
            checkFieldUiState = CheckFieldUiState.None,
            createRecipeUiState = CreateRecipeUiState.None,
            errorUiState = ErrorUiState.None,
            createRecipe = {},
            addPreference = { _, _ -> },
            removePreference = { _, _ -> },
            onBackClick = {},
            onNavigateToRecipe = { _ -> },
            cleanCreateRecipeUiState = {},
            isChatGptApiEnabled = false,
            isNetworkConnected = { true },
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}

@UIModeBakgroundPreview
@Composable
private fun LoadingStatePreview() {
    val fieldsUiState =
        FieldsUiState(
            favoriteIngredients = PreviewParameterData.ingredients,
        )
    fieldsUiState.addField(RecipeFieldState.MEAL, "Jantar")

    ReceptIaTheme {
        CreateRecipeScreen(
            fieldsUiState = fieldsUiState,
            checkFieldUiState = CheckFieldUiState.None,
            createRecipeUiState = CreateRecipeUiState.Loading,
            errorUiState = ErrorUiState.None,
            createRecipe = {},
            addPreference = { _, _ -> },
            removePreference = { _, _ -> },
            onBackClick = {},
            onNavigateToRecipe = { _ -> },
            cleanCreateRecipeUiState = {},
            isChatGptApiEnabled = false,
            isNetworkConnected = { true },
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}
