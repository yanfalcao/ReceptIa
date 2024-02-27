package com.example.database

import androidx.test.core.app.ApplicationProvider
import com.example.database.util.DatabaseTestInstance
import com.nexusfalcao.database.ReceptIaDatabase
import com.nexusfalcao.database.dao.UserDao
import com.nexusfalcao.database.model.UserEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.UUID

@RunWith(RobolectricTestRunner::class)
class UserDaoTest {
    private lateinit var database: ReceptIaDatabase
    private lateinit var userDao: UserDao
    private lateinit var user: UserEntity

    @Before
    fun setupDatabase() {
        database = DatabaseTestInstance(ApplicationProvider.getApplicationContext())
        userDao = database.userDao()
    }

    @Before
    fun setupUser() {
        user = UserEntity(
            id = UUID.randomUUID().toString(),
            name = "Yan",
            photoId = 1234,
            isLoggedIn = true,
        )
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insert_returnTrue() {
        val rowsAffected = userDao.insert(user)

        assert(rowsAffected > 0)
    }

    @Test
    fun updatePhotoId_returnTrue() {
        userDao.insert(user)

        val updatedPhotoId = 4321
        val updatedUser = UserEntity(
            id = user.id,
            name = user.name,
            photoId = updatedPhotoId,
            isLoggedIn = user.isLoggedIn,
        )

        val rowsAffected = userDao.updatePhotoId(updatedUser.id, updatedUser.photoId!!)

        assert(rowsAffected > 0)
    }

    @Test
    fun deleteAll_returnTrue() {
        userDao.insert(user)
        userDao.deleteAll()


        val usersFinded = userDao.findAll()

        assert(usersFinded.isEmpty())
    }
}