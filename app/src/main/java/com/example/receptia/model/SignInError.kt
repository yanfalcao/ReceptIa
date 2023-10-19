package com.example.receptia.model

data class SignInError(
    val message: String?,
    val status: SignInErrorStatus
)

enum class SignInErrorStatus{
    CANCELLED, GENERIC
}
