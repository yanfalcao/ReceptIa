package com.nexusfalcao.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    @ColumnInfo(name = "recipe_steps")
    val recipeSteps: String,
    @ColumnInfo(name = "amount_calories")
    val amountCalories: String,
    @ColumnInfo(name = "amount_carbs")
    val amountCarbs: String,
    @ColumnInfo(name = "amount_proteins")
    val amountProteins: String,
    @ColumnInfo(name = "prep_time")
    val prepTime: String,
    val difficult: String,
    @ColumnInfo("difficult_level")
    val difficultLevel: Int,
    @ColumnInfo("amount_people_serves")
    val amountPeopleServes: Int,
    @ColumnInfo("is_favorite")
    val isFavorite: Boolean,
    @ColumnInfo("created_at")
    var createdAt: Long = Date().time
)
