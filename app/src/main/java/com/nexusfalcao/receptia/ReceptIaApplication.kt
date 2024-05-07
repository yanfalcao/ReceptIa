package com.nexusfalcao.receptia

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.nexusfalcao.receptia.configs.RemoteConfig
import com.nexusfalcao.receptia.configs.RemoteValues
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ReceptIaApplication : Application() {
    companion object {
        lateinit var instance: ReceptIaApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
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
