package com.example.receptia.configs

import com.example.receptia.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings


object RemoteConfig {
    private const val EXPIRATION_DURATION_SECONDS = 1800L // 30 minutes

    private val remoteConfig by lazy { Firebase.remoteConfig }
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = EXPIRATION_DURATION_SECONDS
    }

    init {
        start()
        fetch()
    }

    private fun start() {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_default)
    }

    private fun fetch() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateConfigs()
                } else {
                    task.exception?.printStackTrace()
                }
            }
    }

    fun fetchConfigs() {
        start()
        fetch()
    }

    private fun updateConfigs() {
        RemoteValues.VALUE_CHATGPT_API_ENABLED =
            remoteConfig.getBoolean(RemoteKeys.KEY_CHATGPT_API_ENABLED)
        RemoteValues.VALUE_NON_FATAL_CRASHLYTICS_ENABLE =
            remoteConfig.getBoolean(RemoteKeys.KEY_NON_FATAL_CRASHLYTICS_ENABLE)
        RemoteValues.VALUE_CHATGPT_API_MODEL =
            remoteConfig.getString(RemoteKeys.KEY_CHATGPT_API_MODEL)
        RemoteValues.VALUE_APP_STORE_URL =
            remoteConfig.getString(RemoteKeys.KEY_APP_STORE_URL)
        RemoteValues.VALUE_CURRENT_UPDATE_VERSION =
            remoteConfig.getString(RemoteKeys.KEY_CURRENT_UPDATE_VERSION)
        RemoteValues.KEY_FORCE_APP_UPDATE =
            remoteConfig.getBoolean(RemoteKeys.KEY_FORCE_APP_UPDATE)
    }
}