package com.nexusfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "step")
data class StepEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "recipe_id")
    val recipeId: String,
    val position: Int,
    val description: String,
)
