package com.example.receptia.persistence

import com.example.receptia.persistence.extension.realmCreate
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class Recipe : RealmObject {
    companion object {
        fun find(id: String): Recipe {
            return RealmPersistence.getInstance().query<Recipe>("id == $0", id).find()[0]
        }
    }

    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    @SerializedName("recipe_name")
    var name: String = ""
    var description: String = ""
    var ingredients: RealmList<Ingredient> = realmListOf()
    var recipeSteps: String = ""
    var amountCalories: String = ""
    var amountCarbs: String = ""
    var amountProteins: String = ""
    var prepTime: String = ""
    var easeRecipe: String = ""
    var amountPeopleServes: Int = 0
    var isFavorite: Boolean = false

    suspend fun create() {
        withContext(Dispatchers.IO) {
            realmCreate()
        }
    }
}
