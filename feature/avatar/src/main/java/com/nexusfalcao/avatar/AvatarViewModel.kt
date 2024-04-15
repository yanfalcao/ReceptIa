package com.nexusfalcao.avatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.avatar.state.ImageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AvatarViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _imageUiState = MutableStateFlow<ImageUiState>(ImageUiState.Unselected)
    val imageUiState: StateFlow<ImageUiState> = _imageUiState

    fun selectImage(imageId: Int) {
        viewModelScope.launch {
            when(_imageUiState.value) {
                is ImageUiState.Selected -> {
                    val state = _imageUiState.value as ImageUiState.Selected
                    if(state.imageId == imageId) {
                        _imageUiState.value = ImageUiState.Unselected
                    } else {
                        _imageUiState.value = ImageUiState.Selected(imageId)
                    }
                }
                ImageUiState.Unselected -> {
                    _imageUiState.value = ImageUiState.Selected(imageId)
                }
            }
        }
    }

    fun saveImage() {
        viewModelScope.launch {
            when(_imageUiState.value) {
                is ImageUiState.Selected -> {
                    val state = _imageUiState.value as ImageUiState.Selected

                    val user = userRepository.findUser()
                    user?.let {
                        userRepository.updatePhotoId(it.id, state.imageId)
                    }
                }
                else -> {}
            }
        }
    }
}
