package com.nexusfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "ingredient")
data class IngredientEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "recipe_id")
    val recipeId: String,
    val name: String,
    val measure: String,
)
