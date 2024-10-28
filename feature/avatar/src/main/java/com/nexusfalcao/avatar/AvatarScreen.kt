package com.nexusfalcao.avatar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.avatar.state.ImageUiState
import com.nexusfalcao.avatar.widget.GridListAvatar
import com.nexusfalcao.avatar.widget.SelectButton
import com.nexusfalcao.designsystem.preview.FontSizeAcessibilityPreview
import com.nexusfalcao.designsystem.preview.UIModePreview
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.preview.WindowSizePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.TopBarWidget

@Composable
internal fun AvatarRoute(
    navController: NavController,
    viewModel: AvatarViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val imageUiState by viewModel.imageUiState.collectAsStateWithLifecycle()

    AvatarScreen(
        imageUiState = imageUiState,
        selectImage = viewModel::selectImage,
        saveImage = viewModel::saveImage,
        onBackClick = navController::popBackStack,
        windowSizeClass = windowSizeClass,
    )
}

@Composable
private fun AvatarScreen(
    imageUiState: ImageUiState,
    selectImage: (Int) -> Unit,
    saveImage: () -> Unit,
    onBackClick: () -> Unit,
    windowSizeClass: WindowSizeClass,
) {
    val isSelected = imageUiState is ImageUiState.Selected

    Scaffold(
        topBar = {
            TopBarWidget(
                title = stringResource(id = R.string.avatar),
                drawerEnabled = false,
                onBackClick = onBackClick,
            )
        },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(start = 25.dp, end = 25.dp),
        ) {
            GridListAvatar(
                imageUiState = imageUiState,
                selectImage = selectImage,
                modifier = Modifier.weight(weight = 1.0f),
                windowSizeClass = windowSizeClass,
            )

            SelectButton(
                modifier =
                    Modifier
                        .padding(top = 20.dp, bottom = 20.dp)
                        .align(Alignment.CenterHorizontally),
                onClick = {
                    saveImage()
                    onBackClick()
                },
                windowSizeClass = windowSizeClass,
                isSelected = isSelected,
            )
        }
    }
}

@FontSizeAcessibilityPreview
@UIModePreview
@Composable
@WindowSizePreview
private fun AvatarPreview() {
    ReceptIaTheme {
        AvatarScreen(
            imageUiState = ImageUiState.Selected(imageId = R.drawable.img_man),
            selectImage = {},
            saveImage = {},
            onBackClick = {},
            windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
        )
    }
}
