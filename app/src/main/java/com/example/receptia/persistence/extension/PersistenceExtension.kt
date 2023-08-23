package com.example.receptia.persistence.extension

import com.example.receptia.persistence.RealmPersistence
import io.realm.kotlin.types.RealmObject

internal fun <T : RealmObject> T.realmCreate() {
    RealmPersistence.getInstance().writeBlocking {
        copyToRealm(this@realmCreate)
    }
}
