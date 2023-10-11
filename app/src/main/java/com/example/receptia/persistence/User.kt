package com.example.receptia.persistence

import com.example.receptia.persistence.extension.realmCreate
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

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
    var id: String = UUID.randomUUID().toString()
    var name: String = ""
    var photoId: Int? = null
    var isLoggedIn: Boolean = false

    suspend fun create() {
        withContext(Dispatchers.IO) {
            realmCreate()
        }
    }
}