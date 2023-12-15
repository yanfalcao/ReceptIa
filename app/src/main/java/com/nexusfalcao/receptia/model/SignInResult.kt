package com.nexusfalcao.receptia.model

import com.nexusfalcao.receptia.persistence.User

data class SignInResult(
    val data: User?,
    val error: SignInError?
)
