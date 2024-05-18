package com.nexusfalcao.data.repository

import com.nexusfalcao.data.extensions.asRecipeEntity
import com.nexusfalcao.data.util.MockitoHelper
import com.nexusfalcao.data.util.ModelInstance
import com.nexusfalcao.data.util.NetworkIntance
import com.nexusfalcao.database.dao.IngredientDao
import com.nexusfalcao.database.dao.RecipeDao
import com.nexusfalcao.database.dao.StepDao
import com.nexusfalcao.network.model.response.GptResponse
import com.nexusfalcao.network.retrofit.ChatgptNetworkApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import retrofit2.Response

class RecipeRepositoryTest {
    private val mockRecipeDao = mock(RecipeDao::class.java)
    private val mockIngredientDao = mock(IngredientDao::class.java)
    private val mockStepDao = mock(StepDao::class.java)
    private val mockChatgptNetworkApi = mock(ChatgptNetworkApi::class.java)

    private lateinit var repository: RecipeRepository

    @Before
    fun setup() {
        repository =
            DefaultRecipeRepository(
                recipeDao = mockRecipeDao,
                ingredientDao = mockIngredientDao,
                stepDao = mockStepDao,
                chatgptNetworkApi = mockChatgptNetworkApi,
            )
    }

    @Test
    fun `check if insertRecipe returns true`() {
        val recipe = ModelInstance.recipeModel

        Mockito.`when`(mockRecipeDao.insert(recipe.asRecipeEntity())).thenReturn(1)

        val result = repository.insertRecipe(recipe)

        Assert.assertTrue(result)
    }

    @Test
    fun `check if insertRecipe returns false`() {
        val recipe = ModelInstance.recipeModel

        Mockito.`when`(mockRecipeDao.insert(recipe.asRecipeEntity())).thenReturn(0)

        val result = repository.insertRecipe(recipe)

        Assert.assertFalse(result)
    }

    @Test
    fun `check if findRecipe returns null when DaoFindById returns null`() {
        Mockito.`when`(mockRecipeDao.findById(anyString())).thenReturn(null)

        val result = repository.findRecipe("id")

        Assert.assertNull(result)
    }

    @Test
    fun `check if method callNewRecipe returns recipes when api call succeeds`() =
        runBlocking {
            val response = mock<Response<GptResponse>>()

            Mockito.`when`(mockChatgptNetworkApi.createNewRecipe(MockitoHelper.anyObject()))
                .thenReturn(response)
            Mockito.`when`(response.body())
                .thenReturn(NetworkIntance.gptResponse)

            val result = repository.callNewRecipe(ModelInstance.preference, "apiModel")

            Assert.assertTrue(result.isNotEmpty())
        }

    @Test
    fun `check if method callNewRecipe returns emptyList when api call fails`() =
        runBlocking {
            Mockito.`when`(mockChatgptNetworkApi.createNewRecipe(MockitoHelper.anyObject()))
                .thenThrow(RuntimeException())

            val result = repository.callNewRecipe(ModelInstance.preference, "apiModel")

            Assert.assertTrue(result.isEmpty())
        }
}
