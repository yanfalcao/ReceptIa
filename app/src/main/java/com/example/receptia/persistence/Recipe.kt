package com.example.receptia.persistence

import com.example.receptia.persistence.extension.realmCreate
import com.example.receptia.persistence.utils.DifficultState
import com.google.gson.annotations.SerializedName
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

class Recipe : RealmObject {
    companion object {
        fun find(id: String): Recipe {
            return RealmPersistence.getInstance().query<Recipe>("id == $0", id).find()[0]
        }

        fun find(limit: Int): List<Recipe> {
            return RealmPersistence.getInstance()
                .query<Recipe>()
                .sort("createdAt", Sort.DESCENDING)
                .limit(limit)
                .find()
                .toList()
        }

        fun find(): List<Recipe> {
            return RealmPersistence.getInstance()
                .query<Recipe>()
                .sort("createdAt", Sort.DESCENDING)
                .find()
                .toList()
        }

        suspend fun toogleIsFavorite(id: String) {
            withContext(Dispatchers.IO) {
                val realm = RealmPersistence.getInstance()
                val recipe = realm.query<Recipe>("id == $0", id).find()[0]

                realm.writeBlocking {
                    val recipeToUpdate = findLatest(recipe) ?: error("Cannot find latest version of embedded object")
                    recipeToUpdate.isFavorite = !recipe.isFavorite
                }
            }
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
    var difficult: String = ""
    var difficultLevel: Int = 1
    var amountPeopleServes: Int = 0
    var isFavorite: Boolean = false
    var createdAt: Long = Date().time
    @Ignore
    var difficultState: DifficultState = DifficultState.Easy
        private set
        get() {
            return when(difficultLevel) {
                1 -> DifficultState.Easy
                2 -> DifficultState.Medium
                3 -> DifficultState.Hard
                else -> DifficultState.Easy
            }
        }

    suspend fun create() {
        withContext(Dispatchers.IO) {
            realmCreate()
        }
    }
}
