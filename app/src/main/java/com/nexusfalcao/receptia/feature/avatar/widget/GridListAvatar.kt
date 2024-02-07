package com.nexusfalcao.receptia.feature.avatar.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.R
import com.nexusfalcao.receptia.feature.avatar.state.ImageUiState
import com.nexusfalcao.receptia.ui.widget.OutlineCircularShape

@Composable
fun GridListAvatar(
    modifier: Modifier = Modifier,
    imageUiState: ImageUiState,
    selectImage: (Int) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(getAvatarsImage()) {
            val colorOutline = getOutlineColor(
                imageId = it,
                imageUiState = imageUiState,
                colorScheme = MaterialTheme.colorScheme,
            )

            Box(
                Modifier
                    .clickable { selectImage(it) }
                    .padding(top = 5.dp),
            ) {
                OutlineCircularShape(
                    modifier = Modifier.align(Alignment.Center),
                    shapeSize = 150.dp,
                    color = colorOutline
                )

                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(120.dp),
                )
            }

        }
    }
}

private fun getOutlineColor(
    imageId: Int,
    imageUiState: ImageUiState,
    colorScheme: ColorScheme,
): Color {
    return when(imageUiState) {
        is ImageUiState.Selected -> {
            when(imageUiState.isSelected(imageId)) {
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

@Preview(showBackground = true)
@Composable
private fun GridListAvatarPreview() {
    GridListAvatar(
        imageUiState = ImageUiState.Selected(imageId = 2131230856),
        selectImage = {}
    )
}
