package com.example.receptia.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class SplashViewModel : ViewModel() {

    // TODO: Implement splash logic
    val splashState: StateFlow<SplashUiState> =
        flow<SplashUiState> {
            emit(SplashUiState.Success(logged = true))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SplashUiState.Loading,
        )
}
