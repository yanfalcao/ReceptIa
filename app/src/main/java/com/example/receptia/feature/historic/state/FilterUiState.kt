package com.example.receptia.feature.historic.state

sealed interface FilterUiState {
    data class Filters(
        var tag: TagFilterEnum = TagFilterEnum.ALL,
        var search: String? = null,
    ) : FilterUiState {
        override fun equals(other: Any?): Boolean {
            return false
        }
    }
}
