package com.nexusfalcao.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nexusfalcao.database.model.RecipeEntity
import com.nexusfalcao.database.model.RecipeWithIngredients
import com.nexusfalcao.database.model.UserEntity

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Query("SELECT * FROM recipe WHERE recipe.id = :id")
    fun findById(id: String): UserEntity

    @Query("SELECT * FROM recipe ORDER BY created_at DESC LIMIT :limit")
    fun findLimited(limit: Int): List<RecipeWithIngredients>

    @Transaction
    @Query("SELECT * FROM recipe")
    fun findAll(): List<RecipeWithIngredients>

    @Query("DELETE FROM recipe")
    fun deleteAll()

    @Query("UPDATE recipe SET is_favorite = :isFavorite WHERE id = :recipeId")
    fun updateIsFavorite(recipeId: String, isFavorite: Boolean)
}
