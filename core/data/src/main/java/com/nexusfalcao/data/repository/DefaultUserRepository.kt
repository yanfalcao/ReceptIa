package com.nexusfalcao.data.repository

import android.app.Application
import com.nexusfalcao.data.extensions.asUserEntity
import com.nexusfalcao.data.extensions.asUserModel
import com.nexusfalcao.database.ReceptIaDatabase
import com.nexusfalcao.database.dao.UserDao
import com.nexusfalcao.model.User

internal class DefaultUserRepository(
    private val appContext: Application
) : UserRepository {
    private val userDao: UserDao?
        get() = ReceptIaDatabase.getInstance(appContext)?.userDao()

    override fun findUser(): User? {
        return userDao?.findAll()
            ?.firstOrNull()
            ?.asUserModel()
    }

    override fun insertUser(user: User): Boolean {
        userDao?.deleteAll()
        val rowsAffected = userDao?.insert(user.asUserEntity())

        return rowsAffected != null && rowsAffected > 0
    }

    override fun updatePhotoId(userId: String, photoId: Int): Boolean {
        val rowsAffected = userDao?.updatePhotoId(userId, photoId)

        return rowsAffected != null && rowsAffected > 0
    }
}