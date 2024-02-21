package com.example.database

import androidx.test.core.app.ApplicationProvider
import com.example.database.util.DatabaseTestInstance
import com.nexusfalcao.database.ReceptIaDatabase
import com.nexusfalcao.database.dao.IngredientDao
import com.nexusfalcao.database.dao.RecipeDao
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RecipeDaoTest {
    private lateinit var database: ReceptIaDatabase
    private lateinit var recipeDao: RecipeDao
    private lateinit var ingredientDao: IngredientDao

    @Before
    fun setupDatabase() {
        database = DatabaseTestInstance(ApplicationProvider.getApplicationContext())
        recipeDao = database.recipeDao()
        ingredientDao = database.ingredientDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}
