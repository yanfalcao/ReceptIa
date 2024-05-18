package com.nexusfalcao.data.repository

import com.nexusfalcao.data.util.MockitoHelper
import com.nexusfalcao.data.util.ModelInstance
import com.nexusfalcao.database.dao.UserDao
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

class UserRepositoryTest {
    private val mockUserDao = Mockito.mock(UserDao::class.java)
    private val repository = DefaultUserRepository(mockUserDao)

    @Test
    fun `check if method findUser returns user`() {
        val userEntity = ModelInstance.userEntity

        Mockito.`when`(mockUserDao.findAll())
            .thenReturn(listOf(userEntity))

        val result = repository.findUser()

        Assert.assertNotNull(result)
    }

    @Test
    fun `check if method findUser returns null`() {
        Mockito.`when`(mockUserDao.findAll())
            .thenReturn(emptyList())

        val result = repository.findUser()

        Assert.assertNull(result)
    }

    @Test
    fun `check if method insertUser returns true`() {
        val userModel = ModelInstance.userModel

        Mockito.`when`(mockUserDao.insert(MockitoHelper.anyObject()))
            .thenReturn(1)

        val result = repository.insertUser(userModel)

        Assert.assertTrue(result)
    }

    @Test
    fun `check if method insertUser returns false`() {
        val user = ModelInstance.userModel

        Mockito.`when`(mockUserDao.insert(MockitoHelper.anyObject()))
            .thenReturn(0)

        val result = repository.insertUser(user)

        Assert.assertFalse(result)
    }

    @Test
    fun `check if method updatePhotoId returns true`() {
        Mockito.`when`(mockUserDao.updatePhotoId(anyString(), anyInt()))
            .thenReturn(1)

        val result = repository.updatePhotoId("id", 1)

        Assert.assertTrue(result)
    }

    @Test
    fun `check if method updatePhotoId returns false`() {
        Mockito.`when`(mockUserDao.updatePhotoId(anyString(), anyInt()))
            .thenReturn(0)

        val result = repository.updatePhotoId("id", 1)

        Assert.assertFalse(result)
    }
}
