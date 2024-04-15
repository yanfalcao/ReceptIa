package com.nexusfalcao.home.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nexusfalcao.designsystem.preview.ThemePreview
import com.nexusfalcao.designsystem.theme.ReceptIaTheme
import com.nexusfalcao.designsystem.widget.SkeletonLoadingWidget

@Composable
fun LoadingRecipeList() {
    Column {
        repeat(4) {
            SkeletonLoadingWidget(Modifier.height(100.dp))
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@ThemePreview
@Composable
fun LoadingRecipePreview() {
    ReceptIaTheme {
        LoadingRecipeList()
    }
}
