package com.nexusfalcao.data.repository

import com.nexusfalcao.model.User

interface UserRepository {
    fun getUser(): User?

    fun saveUser(user: User): Boolean

    fun updatePhotoId(userId: String, photoId: Int): Boolean
}