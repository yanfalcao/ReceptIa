package com.example.receptia.feature.home.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.receptia.ui.theme.widget.SkeletonLoadingWidget

@Preview
@Composable
fun LoadingRecipeList() {
    Column {
        repeat(4) {
            SkeletonLoadingWidget(Modifier.height(100.dp))
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}
