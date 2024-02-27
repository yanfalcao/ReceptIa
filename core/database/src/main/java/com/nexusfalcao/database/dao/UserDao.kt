package com.nexusfalcao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexusfalcao.database.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserEntity): Long

    @Query("SELECT * FROM user")
    fun findAll(): List<UserEntity>

    @Query("SELECT * FROM user WHERE user.id = :id")
    fun findById(id: String): UserEntity

    @Query("DELETE FROM user")
    fun deleteAll(): Int

    @Query("UPDATE user SET photo_id = :photoId WHERE id = :userId")
    fun updatePhotoId(userId: String, photoId: Int): Int
}
