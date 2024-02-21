package com.nexusfalcao.model

data class User(
    val id: String,
    val name: String,
    var photoId: Int?,
    var isLoggedIn: Boolean,
)
