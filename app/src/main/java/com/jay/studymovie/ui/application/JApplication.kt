package com.jay.studymovie.ui.application

import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.jay.studymovie.data.AuthRepositoryImpl
import com.jay.studymovie.data.MovieRepositoryImpl
import com.jay.studymovie.data.local.JayDatabase
import com.jay.studymovie.data.remote.NetworkServiceImpl
import com.jay.studymovie.data.local.prefs.PreferencesHelperImpl
import com.jay.studymovie.data.local.source.JayAuthLocalDataSourceImpl
import com.jay.studymovie.data.local.source.JayMovieLocalDataSourceImpl
import com.jay.studymovie.data.remote.source.JayMovieRemoteDataSourceImpl
import com.jay.studymovie.domain.repository.JayAuthRepository
import com.jay.studymovie.domain.repository.JayMovieRepository
import com.uber.rxdogtag.RxDogTag
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException

class JApplication : Application() {
    private val TAG = javaClass.simpleName

    lateinit var movieRepository: JayMovieRepository
    lateinit var authRepository: JayAuthRepository

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        inject()
        RxDogTag.install()
        rxErrorHandler()
    }

    private fun inject() {
        val prefHelper = PreferencesHelperImpl(applicationContext)
        val database = JayDatabase.Factory.create(applicationContext)
        val networkService = NetworkServiceImpl()

        val authLocalDataSource = JayAuthLocalDataSourceImpl(
            prefHelper
        )

        val movieLocalDataSource = JayMovieLocalDataSourceImpl(
            prefHelper,
            database.movieDao
        )

        val movieRemoteDataSource = JayMovieRemoteDataSourceImpl(
            networkService.movieApi
        )

        authRepository = AuthRepositoryImpl(
            authLocalDataSource
        )

        movieRepository = MovieRepositoryImpl(
            movieLocalDataSource,
            movieRemoteDataSource
        )
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