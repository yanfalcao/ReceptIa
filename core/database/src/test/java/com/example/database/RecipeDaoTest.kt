package com.example.database

import androidx.test.core.app.ApplicationProvider
import com.example.database.util.DatabaseTestInstance
import com.nexusfalcao.database.ReceptIaDatabase
import com.nexusfalcao.database.dao.IngredientDao
import com.nexusfalcao.database.dao.RecipeDao
import com.nexusfalcao.database.dao.StepDao
import com.nexusfalcao.database.model.IngredientEntity
import com.nexusfalcao.database.model.RecipeEntity
import com.nexusfalcao.database.model.StepEntity
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
    private lateinit var stepDao: StepDao
    private lateinit var recipe: RecipeEntity
    private lateinit var recipe2: RecipeEntity
    private lateinit var ingredients: List<IngredientEntity>
    private lateinit var ingredients2: List<IngredientEntity>
    private lateinit var steps: List<StepEntity>
    private lateinit var steps2: List<StepEntity>

    @Before
    fun setupDatabase() {
        database = DatabaseTestInstance(ApplicationProvider.getApplicationContext())
        recipeDao = database.recipeDao()
        ingredientDao = database.ingredientDao()
        stepDao = database.stepDao()
    }

    @Before
    fun setupRecipe() {
        recipe = RecipeEntity(
            name = "Sample Recipe",
            description = "This is a sample recipe",
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

        steps = listOf(
            StepEntity(description = "Step 1", position = 1, recipeId = recipe.id),
            StepEntity(description = "Step 2", position = 2, recipeId = recipe.id),
        )

        steps2 = listOf(
            StepEntity(description = "Step 1", position = 1, recipeId = recipe2.id),
            StepEntity(description = "Step 2", position = 2, recipeId = recipe2.id),
            StepEntity(description = "Step 3", position = 3, recipeId = recipe2.id)
        )
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun `test if recipe insert is save`() {
        val rowsAffected = recipeDao.insert(recipe)

        assert(rowsAffected > 0)
    }

    @Test
    fun `test if recife find by id is saved`() {
        recipeDao.insert(recipe)
        ingredients.forEach { item ->
            ingredientDao.insert(item)
        }
        steps.forEach { item ->
            stepDao.insert(item)
        }


        val recipeWithRelations = recipeDao.findById(recipe.id)

        assert(recipeWithRelations?.recipe?.id == recipe.id)
        assert(recipeWithRelations?.ingredients?.size == ingredients.size)
        assert(recipeWithRelations?.steps?.size == steps.size)
    }

    @Test
    fun `test recipe find by id if database is empty`() {
        recipeDao.deleteAll()

        val recipeWithRelations = recipeDao.findById(recipe.id)

        assert(recipeWithRelations == null)
    }

    @Test
    fun `test if recipe find by limit returns correctly`() {
        recipeDao.insert(recipe)

        recipeDao.insert(recipe2)


        val recipeWithRelations = recipeDao.findLimited(1)

        assert(recipeWithRelations.isNotEmpty())
        assert(recipeWithRelations.size == 1)
    }

    @Test
    fun `test if recipe find by limit returns sorted descend by creation date`() {
        recipeDao.insert(recipe)

        recipeDao.insert(recipe2)


        val recipeWithRelations = recipeDao.findLimited(1)

        assert(recipeWithRelations[0].recipe.id == recipe2.id)
    }

    @Test
    fun `test if recipe find all returns correctly`() {
        recipeDao.insert(recipe)
        ingredients.forEach { item ->
            ingredientDao.insert(item)
        }
        steps.forEach { item ->
            stepDao.insert(item)
        }

        recipeDao.insert(recipe2)
        ingredients2.forEach { item ->
            ingredientDao.insert(item)
        }
        steps2.forEach { item ->
            stepDao.insert(item)
        }


        val recipeWithRelations = recipeDao.findAll()

        assert(recipeWithRelations.size == 2)
        assert(recipeWithRelations[0].ingredients.size == ingredients.size)
        assert(recipeWithRelations[1].ingredients.size == ingredients2.size)
        assert(recipeWithRelations[0].steps.size == steps.size)
        assert(recipeWithRelations[1].steps.size == steps2.size)
    }

    @Test
    fun `test recipe find all if database is empty`() {
        recipeDao.deleteAll()

        val recipeWithRelations = recipeDao.findAll()

        assert(recipeWithRelations.isEmpty())
    }

    @Test
    fun `test if recipe update isFavorite column is updated`() {
        recipeDao.insert(recipe)

        val rowsAffected = recipeDao.updateIsFavorite(recipe.id, true)

        assert(rowsAffected == 1)
    }

    @Test
    fun `test if recipe delete all clean the tables`() {
        recipeDao.insert(recipe)
        ingredients.forEach { item ->
            ingredientDao.insert(item)
        }
        steps.forEach { item ->
            stepDao.insert(item)
        }

        recipeDao.insert(recipe2)
        ingredients2.forEach { item ->
            ingredientDao.insert(item)
        }
        steps2.forEach { item ->
            stepDao.insert(item)
        }


        var rowsAffected = recipeDao.deleteAll()
        val recipeWithRelations = recipeDao.findAll()

        assert(rowsAffected > 0)
        assert(recipeWithRelations.isEmpty())

        rowsAffected = ingredientDao.deleteByRecipeId(recipe.id)
        assert(rowsAffected == ingredients.size)

        rowsAffected = ingredientDao.deleteByRecipeId(recipe2.id)
        assert(rowsAffected == ingredients2.size)

        rowsAffected = stepDao.deleteByRecipeId(recipe.id)
        assert(rowsAffected == steps.size)

        rowsAffected = stepDao.deleteByRecipeId(recipe2.id)
        assert(rowsAffected == steps2.size)
    }
}
