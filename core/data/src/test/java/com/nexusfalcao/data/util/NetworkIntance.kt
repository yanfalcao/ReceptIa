package com.nexusfalcao.data.util

import com.nexusfalcao.network.model.response.GptChoices
import com.nexusfalcao.network.model.response.GptFunctionResponse
import com.nexusfalcao.network.model.response.GptMessageResponse
import com.nexusfalcao.network.model.response.GptResponse
import com.nexusfalcao.network.model.response.GptToolCalls
import com.nexusfalcao.network.model.response.GptUsageTokens

object NetworkIntance {
    val toolCalls =
        GptToolCalls(
            id = "id",
            type = "type",
            function =
                GptFunctionResponse(
                    name = "function",
                    arguments = "{\"recipe_name\":\"name\",\"recipe_description\":\"description\",\"amount_calories\":\"100\",\"amount_carbo\":\"20\",\"amount_proteins\":\"30\",\"preparation_time\":\"40\",\"recipe_difficulty\":\"difficult\",\"difficulty_level\":5,\"amount_people_serves\":6,\"preparation_method\":[{\"step\":\"step\"}],\"ingredients\":[{\"name\":\"name\",\"measure\":\"measure\"}]}",
                ),
        )

    val gptResponse =
        GptResponse(
            id = "id",
            typeRequest = "typeRequest",
            usage = GptUsageTokens(1024, 1024, 2048),
            created = 1,
            model = "model",
            choices =
                listOf(
                    GptChoices(
                        index = 1,
                        messageResponse =
                            GptMessageResponse(
                                role = "role",
                                content = "content",
                                toolCalls = listOf(toolCalls),
                            ),
                        logprobs = "logprobs",
                        finishReason = "finishReason",
                    ),
                ),
            systemFingerprint = "systemFingerprint",
        )
}
