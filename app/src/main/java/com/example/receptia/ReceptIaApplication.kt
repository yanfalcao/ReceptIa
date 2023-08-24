package com.example.receptia

import android.app.Application
import com.example.receptia.network.retrofit.RetrofitNetwork
import com.example.receptia.persistence.RealmPersistence
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm

@HiltAndroidApp
class ReceptIaApplication : Application() {
    companion object {
        val http = RetrofitNetwork
        lateinit var persistence: Realm
    }
    override fun onCreate() {
        super.onCreate()
        persistence = RealmPersistence.getInstance()
    }
}
