package com.jay.studymovie.utils.prefs

import android.content.Context
import androidx.core.content.edit

class PreferencesHelperImpl(applicationContext: Context) : PreferencesHelper {

    private val prefs = applicationContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    override var authLogin: Boolean
        get() = prefs.getBoolean(AUTO_LOGIN, true)
        @Synchronized
        set(value) {
            prefs.edit(false) {
                putBoolean(AUTO_LOGIN, value)
            }
        }

    companion object {
        const val FILE_NAME = "dev_wj"

        private const val AUTO_LOGIN = "auto_login"
    }
}