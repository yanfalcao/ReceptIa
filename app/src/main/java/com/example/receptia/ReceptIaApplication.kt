package com.example.receptia

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.receptia.configs.RemoteConfig
import com.example.receptia.configs.RemoteValues
import com.example.receptia.feature.login.GoogleAuthUiClient
import com.example.receptia.network.retrofit.RetrofitNetwork
import com.example.receptia.persistence.RealmPersistence
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
        configRemoteConfig()
        configCrashlytics()
    }

    fun isNetworkConnected(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetwork ?: return false
        val network = manager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            network.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            network.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
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

    private fun configRemoteConfig() {
        RemoteConfig.fetchConfigs()
    }

    private fun configCrashlytics() {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(
            RemoteValues.NON_FATAL_CRASHLYTICS_ENABLE
        )
    }
}
