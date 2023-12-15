package com.nexusfalcao.receptia.feature.historic.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexusfalcao.receptia.ui.widget.SkeletonLoadingWidget

@Composable
fun LoadingRecipeList() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(6) {
            SkeletonLoadingWidget(Modifier.height(145.dp))
        }
    }
}
