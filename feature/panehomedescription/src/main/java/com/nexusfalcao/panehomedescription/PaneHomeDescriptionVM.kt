package com.nexusfalcao.panehomedescription

import androidx.lifecycle.ViewModel
import com.nexusfalcao.data.repository.UserRepository
import com.nexusfalcao.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaneHomeDescriptionVM @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    fun getUser(): User? {
        return userRepository.findUser()
    }
}