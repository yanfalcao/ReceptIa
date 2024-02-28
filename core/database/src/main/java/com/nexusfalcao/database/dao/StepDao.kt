package com.nexusfalcao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nexusfalcao.database.model.StepEntity

@Dao
interface StepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg step: StepEntity)

    @Transaction
    @Query("SELECT * FROM step")
    fun findAll(): List<StepEntity>

    @Query("DELETE FROM step WHERE recipe_id = :recipeId")
    fun deleteByRecipeId(recipeId: String): Int
}