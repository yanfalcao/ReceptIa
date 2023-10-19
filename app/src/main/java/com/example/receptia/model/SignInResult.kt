package com.example.receptia.model

import com.example.receptia.persistence.User

data class SignInResult(
    val data: User?,
    val error: SignInError?
)
