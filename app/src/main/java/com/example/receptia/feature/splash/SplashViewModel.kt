package com.example.receptia.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receptia.ReceptIaApplication
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class SplashViewModel : ViewModel() {
    val splashState: StateFlow<SplashUiState> =
        flow<SplashUiState> {
            val user = ReceptIaApplication.instance.googleAuthUiClient.getSignedInUser()
            val isSignedIn = user != null

            user?.create()

            emit(SplashUiState.Success(logged = isSignedIn))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SplashUiState.Loading,
        )
}
