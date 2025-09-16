package com.nexusfalcao.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nexusfalcao.database.model.RecipeEntity
import com.nexusfalcao.database.model.RecipeWithRelations

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity): Long

    @Transaction
    @Query("SELECT * FROM recipe WHERE recipe.id = :id")
    fun findById(id: String): RecipeWithRelations?

    @Transaction
    @Query("SELECT * FROM recipe ORDER BY created_at DESC LIMIT :limit")
    fun findLimited(limit: Int): List<RecipeWithRelations>

    @Transaction
    @Query("SELECT * FROM recipe")
    fun findAll(): List<RecipeWithRelations>

    @Query("DELETE FROM recipe")
    fun deleteAll(): Int

    @Query("DELETE FROM recipe WHERE id = :recipeId")
    fun delete(recipeId: String): Int

    @Query("UPDATE recipe SET is_favorite = :isFavorite WHERE id = :recipeId")
    fun updateIsFavorite(recipeId: String, isFavorite: Boolean): Int
}
