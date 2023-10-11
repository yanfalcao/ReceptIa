package com.example.receptia.feature.avatar.state

sealed interface ImageUiState {
    object Unselected : ImageUiState

    data class Selected(
        val imageId: Int,
    ) : ImageUiState {
        fun isSelected(imageId: Int): Boolean {
            return this.imageId == imageId
        }
    }
}