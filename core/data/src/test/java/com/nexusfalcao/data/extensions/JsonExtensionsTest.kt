package com.nexusfalcao.data.extensions

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.junit.Assert
import org.junit.Test

class JsonExtensionsTest {
    @Test
    fun `check if getString returns correct when called with valid key`() {
        val jsonObject =
            JsonObject().apply {
                addProperty("key", "value")
            }

        val result = jsonObject.getString("key")

        Assert.assertEquals("value", result)
    }

    @Test(expected = NullPointerException::class)
    fun `check if getString throws exception when called with invalid key`() {
        val jsonObject = JsonObject()

        jsonObject.getString("key")
    }

    @Test
    fun `check if getInt returns correct when called with valid key`() {
        val jsonObject =
            JsonObject().apply {
                addProperty("key", 1)
            }

        val result = jsonObject.getInt("key")

        Assert.assertEquals(1, result)
    }

    @Test(expected = NullPointerException::class)
    fun `check if getInt throws exception when called with invalid key`() {
        val jsonObject = JsonObject()

        jsonObject.getInt("key")
    }

    @Test
    fun `check if getJsonObject returns correct when called with valid index`() {
        val jsonArray =
            JsonArray().apply {
                add(
                    JsonObject().apply {
                        addProperty("key", "value")
                    },
                )
            }

        val result = jsonArray.getJsonObject(0)

        Assert.assertEquals("value", result.getString("key"))
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `check if getJsonObject throws exception when called with invalid index`() {
        val jsonArray = JsonArray()

        jsonArray.getJsonObject(0)
    }
}
