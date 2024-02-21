package com.nexusfalcao.database

import android.app.Activity
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.nexusfalcao.database.dao.IngredientDao
import com.nexusfalcao.database.dao.RecipeDao
import com.nexusfalcao.database.dao.UserDao
import com.nexusfalcao.database.model.IngredientEntity
import com.nexusfalcao.database.model.RecipeEntity
import com.nexusfalcao.database.model.UserEntity


@Database(
    version = 1,
    entities = [
        UserEntity::class,
        IngredientEntity::class,
        RecipeEntity::class
    ]
)
abstract class ReceptIaDatabase : RoomDatabase() {
    companion object {
        private val DATABASE_NAME = "ReceptIaDatabase"
        private var database: ReceptIaDatabase? = null

        fun getInstance(activity: Activity?): ReceptIaDatabase? {
            if (database == null) {
                database = databaseBuilder(activity!!, ReceptIaDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return database
        }
    }

    abstract fun userDao(): UserDao
    abstract fun recipeDao(): RecipeDao
    abstract fun ingredientDao(): IngredientDao
}