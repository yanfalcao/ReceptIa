package com.nexusfalcao.data.extensions

import com.google.gson.JsonParseException
import com.nexusfalcao.data.util.NetworkIntance
import com.nexusfalcao.network.model.response.GptChoices
import com.nexusfalcao.network.model.response.GptFunctionResponse
import com.nexusfalcao.network.model.response.GptMessageResponse
import com.nexusfalcao.network.model.response.GptResponse
import com.nexusfalcao.network.model.response.GptToolCalls
import com.nexusfalcao.network.model.response.GptUsageTokens
import org.junit.Assert
import org.junit.Test

class GptResponseExtensionsTest {
    @Test
    fun `check if getRecipes returns correct recipes when response is valid`() {
        val gptResponse = NetworkIntance.gptResponse

        val result = gptResponse.getRecipes()

        Assert.assertEquals(1, result.size)
        Assert.assertEquals("name", result[0].name)
        Assert.assertEquals("description", result[0].description)
        Assert.assertEquals("100", result[0].recipeDetails.amountCalories)
        Assert.assertEquals("20", result[0].recipeDetails.amountCarbs)
        Assert.assertEquals("30", result[0].recipeDetails.amountProteins)
        Assert.assertEquals("40", result[0].recipeDetails.preparationTime)
        Assert.assertEquals("difficult", result[0].recipeDetails.difficult)
        Assert.assertEquals(5, result[0].recipeDetails.difficultLevel)
        Assert.assertEquals(6, result[0].recipeDetails.amountPeopleServes)
        Assert.assertEquals(1, result[0].recipeSteps.size)
        Assert.assertEquals("step", result[0].recipeSteps[0].description)
        Assert.assertEquals(1, result[0].ingredients.size)
        Assert.assertEquals("name", result[0].ingredients[0].name)
        Assert.assertEquals("measure", result[0].ingredients[0].measure)
    }

    @Test(expected = JsonParseException::class)
    fun `check if getRecipes throws exception when response is invalid`() {
        val toolCalls =
            listOf(
                GptToolCalls(
                    id = null,
                    type = null,
                    function =
                        GptFunctionResponse(
                            name = "function",
                            arguments = "invalid json",
                        ),
                ),
            )
        val messageResponse = GptMessageResponse(null, null, toolCalls)
        val gptResponse =
            GptResponse(
                id = "id",
                typeRequest = null,
                usage = GptUsageTokens(1024, 1024, 2048),
                created = 1,
                model = "model",
                choices =
                    listOf(
                        GptChoices(
                            index = 1,
                            messageResponse = messageResponse,
                            logprobs = null,
                            finishReason = null,
                        ),
                    ),
                systemFingerprint = null,
            )

        gptResponse.getRecipes()
    }
}
