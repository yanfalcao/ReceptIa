package com.example.panecatalogdescription.state

import com.nexusfalcao.model.state.RecipeDifficult
import com.nexusfalcao.recipecatalog.state.AmountServesFilterEnum
import com.nexusfalcao.recipecatalog.state.CatalogUiState
import com.nexusfalcao.recipecatalog.state.FilterState
import com.nexusfalcao.recipecatalog.state.TagFilterEnum

data class ListPaneState(
    val catalogUiState: CatalogUiState,
    val filterState: FilterState,
    val updateTagFilter: (TagFilterEnum) -> Unit = {},
    val updateSearchFilter: (String) -> Unit = {},
    val updateDifficultFilter: (RecipeDifficult) -> Unit = {},
    val updateAmountServesFilter: (AmountServesFilterEnum) -> Unit = {},
    val onApplyFilter: () -> Unit = {},
    val onResetFilter: () -> Unit = {},
)
