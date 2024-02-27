package com.nexusfalcao.data.extensions

import com.nexusfalcao.database.model.UserEntity
import com.nexusfalcao.model.User

internal fun UserEntity.asUserModel() = User(
    id = this.id,
    name = this.name,
    photoId = this.photoId,
    isLoggedIn = this.isLoggedIn
)

internal fun User.asUserEntity() = UserEntity(
    id = this.id,
    name = this.name,
    photoId = this.photoId,
    isLoggedIn = this.isLoggedIn,
)