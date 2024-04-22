package com.nexusfalcao.createrecipe.state

sealed interface RadioUiState {
    object Unselected : RadioUiState

    data class Selected(
        val textOption: String,
    ) : RadioUiState
}
