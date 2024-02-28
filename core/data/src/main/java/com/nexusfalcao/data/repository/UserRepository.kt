package com.nexusfalcao.data.repository

import com.nexusfalcao.model.User

interface UserRepository {
    fun findUser(): User?

    fun insertUser(user: User): Boolean

    fun updatePhotoId(userId: String, photoId: Int): Boolean
}