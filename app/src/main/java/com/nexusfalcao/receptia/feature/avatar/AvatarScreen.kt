package com.nexusfalcao.receptia.feature.avatar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.feature.avatar.state.ImageUiState
import com.nexusfalcao.receptia.feature.avatar.widget.GridListAvatar
import com.nexusfalcao.receptia.ui.preview.ThemePreviewShowsBakground
import com.nexusfalcao.receptia.ui.theme.ReceptIaTheme

@Composable
internal fun AvatarRoute(
    navController: NavController,
    viewModel: AvatarViewModel = hiltViewModel(),
) {
    val imageUiState by viewModel.imageUiState.collectAsStateWithLifecycle()

    AvatarScreen(
        imageUiState = imageUiState,
        selectImage = viewModel::selectImage,
        saveImage = viewModel::saveImage,
        onBackClick = navController::popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AvatarScreen(
    imageUiState: ImageUiState,
    selectImage: (Int) -> Unit,
    saveImage: () -> Unit,
    onBackClick: () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val isSelected = imageUiState is ImageUiState.Selected

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorScheme.background),
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = colorScheme.onBackground
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(start = 25.dp, end = 25.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.select_you_avatar),
                color = colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

            GridListAvatar(
                imageUiState = imageUiState,
                selectImage = selectImage,
                modifier = Modifier.weight(weight = 1.0f)
            )

            Button(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    saveImage()
                    onBackClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary),
                enabled = isSelected,
            ) {
                Text(
                    text = stringResource(id = R.string.select),
                    color = colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@ThemePreviewShowsBakground
@Composable
private fun AvatarScreenPreview() {
    ReceptIaTheme {
        AvatarScreen(
            imageUiState = ImageUiState.Selected(imageId = 2131230856),
            selectImage = {},
            saveImage = {},
            onBackClick = {}
        )
    }
}
