package com.example.receptia.persistence

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmPersistence private constructor() {
    companion object {
        @Volatile
        private var instance: Realm? = null
        private val dbFileName = "receptia-storage"
        fun getInstance(): Realm =
            instance ?: synchronized(this) {
                instance ?: createInstance().also { instance = it }
            }

        private fun createInstance(): Realm {
            val realmConfig = RealmConfiguration
                .Builder(schema = setOf(Recipe::class, Ingredient::class))
                .name(dbFileName)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()

            return Realm.open(configuration = realmConfig)
        }
    }
}