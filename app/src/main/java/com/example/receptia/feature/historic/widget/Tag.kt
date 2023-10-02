package com.example.receptia.feature.historic.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.receptia.R
import com.example.receptia.feature.historic.state.FilterUiState
import com.example.receptia.feature.historic.state.TagFilterEnum
import com.example.receptia.ui.theme.Green
import com.example.receptia.ui.theme.LightGray

@Composable
fun Tag(
    tagFilter: TagFilterEnum,
    filterUiState: FilterUiState,
    updateTagFilter: (TagFilterEnum) -> Unit = {}
) {
    val tagString = when(tagFilter) {
        TagFilterEnum.ALL -> stringResource(id = R.string.all)
        TagFilterEnum.FAVORITES -> stringResource(id = R.string.favorites)
    }

    val isSelected = when(filterUiState) {
        is FilterUiState.Filters -> {
            filterUiState.tag == tagFilter
        }
    }
    val backgroundColor = when(isSelected) {
        true -> Green
        false -> LightGray
    }
    val textColor = when(isSelected) {
        true -> Color.White
        false -> Color.Black
    }

    Box(
        modifier = Modifier
            .clickable { updateTagFilter(tagFilter) }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(30.dp),
            )
            .padding(vertical = 5.dp, horizontal = 15.dp),
    ) {
        Text(
            text = tagString,
            style = MaterialTheme.typography.titleSmall,
            color = textColor,
        )
    }
}
