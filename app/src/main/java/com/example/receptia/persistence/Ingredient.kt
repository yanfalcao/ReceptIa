package com.example.receptia.persistence

import com.example.receptia.persistence.extension.realmCreate
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.UUID

class Ingredient : RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var name: String = ""
    var measure: String = ""

    fun create() {
        realmCreate()
    }
}
