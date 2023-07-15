package com.example.receptia.feature.newRecipe.state

sealed interface RadioUiState {
    object Unselected : RadioUiState

    data class Selected(
        val textOption: String,
    ) : RadioUiState
}
