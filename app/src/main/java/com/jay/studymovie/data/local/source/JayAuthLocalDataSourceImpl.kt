package com.jay.studymovie.data.local.source

import com.jay.studymovie.data.local.prefs.PreferencesHelper

class JayAuthLocalDataSourceImpl(
    private val pref: PreferencesHelper
) : JayAuthLocalDataSource {
    override val autoLogin: Boolean
        get() = pref.authLogin

    override fun saveAuth() {
        pref.authLogin = false
    }
}