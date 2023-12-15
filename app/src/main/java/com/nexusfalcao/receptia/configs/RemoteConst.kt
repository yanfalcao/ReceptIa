package com.nexusfalcao.receptia.configs

object RemoteValues {
    var VALUE_CHATGPT_API_ENABLED: Boolean = true
    var VALUE_NON_FATAL_CRASHLYTICS_ENABLE: Boolean = true
    var VALUE_CHATGPT_API_MODEL: String = "gpt-3.5-turbo-1106"
    var VALUE_APP_STORE_URL: String = ""
    var VALUE_CURRENT_UPDATE_VERSION: String = "1.0"
    var KEY_FORCE_APP_UPDATE: Boolean = false
}

object RemoteKeys {
    var KEY_CHATGPT_API_ENABLED = "CHATGPT_API_ENABLED"
    var KEY_NON_FATAL_CRASHLYTICS_ENABLE = "NON_FATAL_CRASHLYTICS_ENABLE"
    var KEY_CHATGPT_API_MODEL = "CHATGPT_API_MODEL"
    var KEY_APP_STORE_URL = "APP_STORE_URL"
    var KEY_CURRENT_UPDATE_VERSION = "CURRENT_VERSION"
    var KEY_FORCE_APP_UPDATE = "FORCE_APP_UPDATE"
}