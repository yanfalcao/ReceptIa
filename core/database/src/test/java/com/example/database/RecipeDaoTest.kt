package com.example.database

import androidx.test.core.app.ApplicationProvider
import com.example.database.util.DatabaseTestInstance
import com.nexusfalcao.database.ReceptIaDatabase
import com.nexusfalcao.database.dao.IngredientDao
import com.nexusfalcao.database.dao.RecipeDao
import com.nexusfalcao.database.model.IngredientEntity
import com.nexusfalcao.database.model.RecipeEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RecipeDaoTest {
    private lateinit var database: ReceptIaDatabase
    private lateinit var recipeDao: RecipeDao
    private lateinit var ingredientDao: IngredientDao
    private lateinit var recipe: RecipeEntity
    private lateinit var recipe2: RecipeEntity
    private lateinit var ingredients: List<IngredientEntity>
    private lateinit var ingredients2: List<IngredientEntity>

    @Before
    fun setupDatabase() {
        database = DatabaseTestInstance(ApplicationProvider.getApplicationContext())
        recipeDao = database.recipeDao()
        ingredientDao = database.ingredientDao()
    }

    @Before
    fun setupRecipe() {
        recipe = RecipeEntity(
            name = "Sample Recipe",
            description = "This is a sample recipe",
            recipeSteps = "Step 1, Step 2, Step 3",
            amountCalories = "500",
            amountCarbs = "50g",
            amountProteins = "20g",
            prepTime = "30 minutes",
            difficult = "Medium",
            difficultLevel = 3,
            amountPeopleServes = 4,
            isFavorite = false
        )

        recipe2 = RecipeEntity(
            name = "Sample Recipe",
            description = "This is a sample recipe",
            recipeSteps = "Step 1, Step 2, Step 3",
            amountCalories = "500",
            amountCarbs = "50g",
            amountProteins = "20g",
            prepTime = "30 minutes",
            difficult = "Medium",
            difficultLevel = 3,
            amountPeopleServes = 4,
            isFavorite = false,
            createdAt = recipe.createdAt + 10
        )

        ingredients = listOf(
            IngredientEntity(name = "Ingredient 1", measure = "100g", recipeId = recipe.id),
            IngredientEntity(name = "Ingredient 2", measure = "200g", recipeId = recipe.id),
            IngredientEntity(name = "Ingredient 3", measure = "50g", recipeId = recipe.id)
        )

        ingredients2 = listOf(
            IngredientEntity(name = "Ingredient 1", measure = "100g", recipeId = recipe2.id),
            IngredientEntity(name = "Ingredient 2", measure = "200g", recipeId = recipe2.id),
        )
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insert_returnTrue() {
        val rowsAffected = recipeDao.insert(recipe)

        assert(rowsAffected > 0)
    }

    @Test
    fun findById_returnTrue() {
        recipeDao.insert(recipe)
        ingredients.forEach { item ->
            ingredientDao.insert(item)
        }


        val recipeWithIngredients = recipeDao.findById(recipe.id)

        assert(recipeWithIngredients.recipe.id == recipe.id)
        assert(recipeWithIngredients.ingredients.size == ingredients.size)
    }

    @Test
    fun findLimited_returnTrue() {
        recipeDao.insert(recipe)
        ingredients.forEach { item ->
            ingredientDao.insert(item)
        }

        recipeDao.insert(recipe2)
        ingredients2.forEach { item ->
            ingredientDao.insert(item)
        }


        val recipeWithIngredients = recipeDao.findLimited(1)

        assert(recipeWithIngredients.isNotEmpty())
        assert(recipeWithIngredients.size == 1)
        assert(recipeWithIngredients[0].recipe.id == recipe2.id)
    }

    @Test
    fun findAll_returnTrue() {
        recipeDao.insert(recipe)
        ingredients.forEach { item ->
            ingredientDao.insert(item)
        }

        recipeDao.insert(recipe2)
        ingredients2.forEach { item ->
            ingredientDao.insert(item)
        }


        val recipeWithIngredients = recipeDao.findAll()

        assert(recipeWithIngredients.size == 2)
        assert(recipeWithIngredients[0].ingredients.size == ingredients.size)
        assert(recipeWithIngredients[1].ingredients.size == ingredients2.size)
    }

    @Test
    fun updateIsFavorite_returnTrue() {
        recipeDao.insert(recipe)

        val rowsAffected = recipeDao.updateIsFavorite(recipe.id, true)

        assert(rowsAffected == 1)
    }

    @Test
    fun deleteAll_returnTrue() {
        recipeDao.insert(recipe)
        ingredients.forEach { item ->
            ingredientDao.insert(item)
        }

        recipeDao.insert(recipe2)
        ingredients2.forEach { item ->
            ingredientDao.insert(item)
        }


        var rowsAffected = recipeDao.deleteAll()
        val recipeWithIngredients = recipeDao.findAll()

        assert(rowsAffected > 0)
        assert(recipeWithIngredients.isEmpty())

        rowsAffected = ingredientDao.deleteByRecipeId(recipe.id)
        assert(rowsAffected == ingredients.size)

        rowsAffected = ingredientDao.deleteByRecipeId(recipe2.id)
        assert(rowsAffected == ingredients2.size)
    }
}
