package com.nexusfalcao.receptia.utils

import android.content.Context
import android.content.pm.PackageManager
import com.nexusfalcao.receptia.configs.RemoteValues

object UpdateAppUtil {
    fun requiredUpdate(context: Context) : Boolean {
        val appVersion = getAppVersion(context)
        val currentVersion = RemoteValues.VALUE_CURRENT_UPDATE_VERSION
        val forceUpdate = RemoteValues.KEY_FORCE_APP_UPDATE
        if (forceUpdate && (currentVersion > appVersion)) {
            return true
        }
        return false
    }

    fun getAppVersion(context: Context): String {
        var result = ""
        try {
            result = context.packageManager.getPackageInfo(context.packageName, 0).versionName
            result = result.replace("[a-zA-Z]|-".toRegex(), "")
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return result
    }
}