package com.example.receptia.feature.historic.state

sealed interface FilterUiState {
    data class Filters(
        var tag: TagFilterEnum,
    ) : FilterUiState
}