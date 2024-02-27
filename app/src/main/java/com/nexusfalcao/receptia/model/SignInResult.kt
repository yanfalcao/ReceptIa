package com.nexusfalcao.receptia.model

import com.nexusfalcao.model.User

data class SignInResult(
    val data: User?,
    val error: SignInError?
)
