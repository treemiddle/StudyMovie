package com.jay.studymovie.ui.application

import android.app.Application
import com.jay.studymovie.network.NetworkService
import com.jay.studymovie.utils.prefs.PreferencesHelper
import com.jay.studymovie.utils.prefs.PreferencesHelperImpl

class JApplication : Application() {
    lateinit var preferencesHelper: PreferencesHelper
    //lateinit var networkService: NetworkService

    override fun onCreate() {
        super.onCreate()

        preferencesHelper = PreferencesHelperImpl(applicationContext)
        //networkService = NetworkService
    }
}