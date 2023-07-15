package com.example.receptia.feature.newRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.feature.newRecipe.state.RadioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewRecipeViewModel : ViewModel() {
    private val _radioUiState = MutableStateFlow<RadioUiState>(RadioUiState.Unselected)
    val radioUiState get() = _radioUiState

    fun selectRadio(text: String) {
        viewModelScope.launch {
            _radioUiState.value = RadioUiState.Selected(textOption = text)
        }
    }
}
