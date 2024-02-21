package com.nexusfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val name: String?,
    @ColumnInfo(name = "photo_id")
    val photoId: Int?,
    @ColumnInfo(name = "is_logged_in")
    val isLoggedIn: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (other != null && other is UserEntity) {
            if (this.id == other.id
                && this.name == other.name
                && this.photoId == other.photoId
                && this.isLoggedIn == other.isLoggedIn
            ) {
                return true
            }
        }
        return false
    }
}
