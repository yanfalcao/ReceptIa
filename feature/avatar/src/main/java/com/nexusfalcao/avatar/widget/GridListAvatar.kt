package com.nexusfalcao.avatar.widget

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.nexusfalcao.avatar.R
import com.nexusfalcao.avatar.state.ImageUiState
import com.nexusfalcao.designsystem.extension.hasCompactSize
import com.nexusfalcao.designsystem.extension.hasMediumSize
import com.nexusfalcao.designsystem.preview.UtilPreview
import com.nexusfalcao.designsystem.widget.OutlineCircularShape

@Composable
fun GridListAvatar(
    modifier: Modifier = Modifier,
    imageUiState: ImageUiState,
    windowSizeClass: WindowSizeClass,
    selectImage: (Int) -> Unit,
) {
    val avatarSize: Dp
    val tileSize: Dp

    if (windowSizeClass.hasCompactSize()) {
        avatarSize = 120.dp
        tileSize = 150.dp
    } else if (windowSizeClass.hasMediumSize()) {
        avatarSize = 180.dp
        tileSize = 225.dp
    } else {
        avatarSize = 210.dp
        tileSize = 265.dp
    }

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = tileSize),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(getAvatarsImage()) {
            val colorOutline =
                getOutlineColor(
                    imageId = it,
                    imageUiState = imageUiState,
                    colorScheme = MaterialTheme.colorScheme,
                )

            Box(
                Modifier
                    .clickable { selectImage(it) }
                    .padding(top = 5.dp).widthIn(max = 400.dp),
            ) {
                OutlineCircularShape(
                    modifier = Modifier.align(Alignment.Center),
                    shapeSize = tileSize,
                    color = colorOutline,
                )

                Image(
                    painter = painterResource(id = it),
                    contentDescription = stringResource(
                        id = getContentDescriptionAvatar(
                            imageId = it,
                            imageUiState = imageUiState,
                        ),
                    ),
                    modifier =
                        Modifier
                            .align(Alignment.Center)
                            .size(avatarSize),
                )
            }
        }
    }
}

private fun getContentDescriptionAvatar(
    imageId: Int,
    imageUiState: ImageUiState,
): Int {
    return when (imageUiState) {
        is ImageUiState.Selected -> {
            when (imageUiState.isSelected(imageId)) {
                true -> R.string.cd_avatar_selected
                false -> R.string.cd_avatar_unselected
            }
        }
        ImageUiState.Unselected -> R.string.cd_avatar_unselected
    }
}

private fun getOutlineColor(
    imageId: Int,
    imageUiState: ImageUiState,
    colorScheme: ColorScheme,
): Color {
    return when (imageUiState) {
        is ImageUiState.Selected -> {
            when (imageUiState.isSelected(imageId)) {
                true -> colorScheme.primary
                false -> Color.Transparent
            }
        }
        ImageUiState.Unselected -> Color.Transparent
    }
}

private fun getAvatarsImage(): List<Int> {
    return listOf(
        R.drawable.img_man,
        R.drawable.img_woman_1,
        R.drawable.img_man_2,
        R.drawable.img_woman_2,
        R.drawable.img_aged_1,
        R.drawable.img_woman_3,
        R.drawable.img_aged_2,
        R.drawable.img_woman_4,
        R.drawable.img_man_3,
        R.drawable.img_woman_5,
        R.drawable.img_man_4,
        R.drawable.img_woman_6,
        R.drawable.img_man_5,
        R.drawable.img_woman_7,
        R.drawable.img_man_6,
        R.drawable.img_woman_8,
        R.drawable.img_man_7,
        R.drawable.img_woman_9,
        R.drawable.img_man_8,
        R.drawable.img_woman_10,
    )
}

@Preview(showBackground = true, device = Devices.DESKTOP)
@Composable
private fun GridListAvatarPreview() {
    GridListAvatar(
        imageUiState = ImageUiState.Selected(imageId = R.drawable.img_man),
        selectImage = {},
        windowSizeClass = UtilPreview.getPreviewWindowSizeClass(),
    )
}
