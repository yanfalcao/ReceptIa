package com.nexusfalcao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nexusfalcao.database.model.IngredientEntity

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg ingredients: IngredientEntity)

    @Transaction
    @Query("SELECT * FROM ingredient")
    fun findAll(): List<IngredientEntity>

    @Query("DELETE FROM ingredient WHERE recipe_id = :recipeId")
    fun deleteByRecipeId(recipeId: String): Int
}
