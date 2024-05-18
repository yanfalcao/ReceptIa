package com.nexusfalcao.data.extensions

import com.nexusfalcao.data.util.ModelInstance
import org.junit.Assert
import org.junit.Test

class UserExtensionsTest {
    @Test
    fun `check if user entity to user model conversion is correct`() {
        val userEntity = ModelInstance.userEntity

        val user = userEntity.asUserModel()

        Assert.assertEquals(userEntity.id, user.id)
        Assert.assertEquals(userEntity.name, user.name)
        Assert.assertEquals(userEntity.photoId, user.photoId)
        Assert.assertEquals(userEntity.isLoggedIn, user.isLoggedIn)
    }

    @Test
    fun `check if user model to user entity conversion is correct`() {
        val user = ModelInstance.userModel

        val userEntity = user.asUserEntity()

        Assert.assertEquals(user.id, userEntity.id)
        Assert.assertEquals(user.name, userEntity.name)
        Assert.assertEquals(user.photoId, userEntity.photoId)
        Assert.assertEquals(user.isLoggedIn, userEntity.isLoggedIn)
    }
}
