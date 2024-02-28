package com.nexusfalcao.receptia.utils

import com.google.gson.JsonArray
import com.google.gson.JsonObject

fun JsonObject.getString(key: String) = get(key).asString

fun JsonObject.getInt(key: String) = get(key).asInt

fun JsonArray.getJsonObject(index: Int) = get(index).asJsonObject
