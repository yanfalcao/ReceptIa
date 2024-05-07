package com.nexusfalcao.receptia.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.authentication.GoogleAuthenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val googleAuthenticator: GoogleAuthenticator
) : ViewModel() {
    val splashState: StateFlow<SplashUiState> =
        flow<SplashUiState> {
            emit(SplashUiState.Success(googleAuthenticator.isUserLoggedIn()))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SplashUiState.Loading,
        )
}
