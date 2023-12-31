package com.nexusfalcao.receptia.feature.avatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexusfalcao.receptia.feature.avatar.state.ImageUiState
import com.nexusfalcao.receptia.persistence.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AvatarViewModel : ViewModel() {
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

                    User.find().update(photoId = state.imageId)
                }
                else -> {}
            }
        }
    }
}
