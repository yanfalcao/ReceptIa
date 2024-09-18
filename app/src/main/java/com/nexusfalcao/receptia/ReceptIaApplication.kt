package com.nexusfalcao.receptia

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.telephony.TelephonyManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nexusfalcao.receptia.configs.RemoteConfig
import com.nexusfalcao.receptia.configs.RemoteValues
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
            network.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) &&
                checkCellularSignalStrength() -> true
            network.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun checkCellularSignalStrength(): Boolean {
        val telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val signalStrength = telephonyManager.signalStrength
        val poorStrength = 1

        if (signalStrength != null) {
            val level = signalStrength.level // Get the signal level (0-4)

            return level > poorStrength
        }

        return false
    }

    private fun configRemoteConfig() {
        RemoteConfig.fetchConfigs()
    }

    private fun configCrashlytics() {
        val crashlytics = FirebaseCrashlytics.getInstance()
        crashlytics.setCrashlyticsCollectionEnabled(
            RemoteValues.VALUE_NON_FATAL_CRASHLYTICS_ENABLE,
        )
    }
}
