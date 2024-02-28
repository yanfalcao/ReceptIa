package com.nexusfalcao.receptia

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.nexusfalcao.receptia.configs.RemoteConfig
import com.nexusfalcao.receptia.configs.RemoteValues
import com.nexusfalcao.receptia.feature.login.GoogleAuthUiClient
import com.nexusfalcao.receptia.network.retrofit.RetrofitNetwork
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReceptIaApplication : Application() {
    companion object {
        lateinit var instance: ReceptIaApplication
    }

    lateinit var http: RetrofitNetwork
    lateinit var googleAuthUiClient: GoogleAuthUiClient

    override fun onCreate() {
        super.onCreate()
        instance = this
        configHttp()
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
            RemoteValues.VALUE_NON_FATAL_CRASHLYTICS_ENABLE
        )
    }
}
