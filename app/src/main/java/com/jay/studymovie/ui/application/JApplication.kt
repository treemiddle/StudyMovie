package com.jay.studymovie.ui.application

import android.app.Application
import android.util.Log
import com.jay.studymovie.network.NetworkService
import com.jay.studymovie.network.NetworkServiceImpl
import com.jay.studymovie.utils.prefs.PreferencesHelper
import com.jay.studymovie.utils.prefs.PreferencesHelperImpl
import com.uber.rxdogtag.RxDogTag
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException

class JApplication : Application() {
    private val TAG = javaClass.simpleName
    lateinit var preferencesHelper: PreferencesHelper
    lateinit var networkService: NetworkService

    override fun onCreate() {
        super.onCreate()

        RxDogTag.install()
        rxErrorHandler()

        preferencesHelper = PreferencesHelperImpl(applicationContext)
        networkService = NetworkServiceImpl()
    }

    private fun rxErrorHandler() {

        RxJavaPlugins.setErrorHandler { e ->
            Log.d(TAG, "rxErrorHandler: $e")
            var error = e
            if (error is UndeliverableException) {
                error = e.cause
            }
            if (error is IOException || error is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (error is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (error is NullPointerException || error is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            if (error is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), error)
                return@setErrorHandler
            }
            Log.d(TAG, "Undeliverable exception received, not sure what to do", error)
        }
    }
}