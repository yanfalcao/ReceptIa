package com.example.receptia.persistence

import com.example.receptia.persistence.extension.realmCreate
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

class Recipe : RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
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

    fun create() {
        realmCreate()
    }
}
