package com.nexusfalcao.receptia.persistence

import com.nexusfalcao.receptia.persistence.extension.realmCreate
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class User : RealmObject {
    companion object {
        fun find(id: String): User {
            return RealmPersistence.getInstance().query<User>("id == $0", id).find()[0]
        }

        fun find(): User {
            return RealmPersistence.getInstance()
                .query<User>()
                .find()
                .toList()
                .first()
        }
    }

    @PrimaryKey
    var id: String = ""
    var name: String? = null
    var photoId: Int? = null
    var isLoggedIn: Boolean = false

    suspend fun create() {
        withContext(Dispatchers.IO) {
            RealmPersistence.getInstance().writeBlocking { deleteAll() }
            realmCreate()
        }
    }

    suspend fun update(photoId: Int) {
        withContext(Dispatchers.IO) {
            val realm = RealmPersistence.getInstance()
            val recipe = realm.query<User>("id == $0", id).find()[0]

            realm.writeBlocking {
                val recipeToUpdate = findLatest(recipe) ?: error("Cannot find latest version of embedded object")
                recipeToUpdate.photoId = photoId
            }
        }
    }
}