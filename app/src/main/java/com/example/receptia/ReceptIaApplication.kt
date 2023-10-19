package com.example.receptia

import android.app.Application
import com.example.receptia.feature.login.GoogleAuthUiClient
import com.example.receptia.network.retrofit.RetrofitNetwork
import com.example.receptia.persistence.RealmPersistence
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm

@HiltAndroidApp
class ReceptIaApplication : Application() {
    companion object {
        lateinit var instance: ReceptIaApplication
    }

    lateinit var http: RetrofitNetwork
    lateinit var persistence: Realm
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    override fun onCreate() {
        super.onCreate()
        instance = this
        configHttp()
        configPersistence()
        configGoogleAuth()
    }

    private fun configHttp() {
        http = RetrofitNetwork
    }

    private fun configPersistence() {
        persistence = RealmPersistence.getInstance()
    }

    private fun configGoogleAuth() {
        googleAuthUiClient = GoogleAuthUiClient(
            oneTapClient = Identity.getSignInClient(this)
        )
    }
}
