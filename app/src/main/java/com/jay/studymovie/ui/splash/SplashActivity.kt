package com.jay.studymovie.ui.splash

import android.content.Intent
import android.os.Bundle
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.login.LoginActivity
import com.jay.studymovie.ui.main.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {
    private val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        status()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun status() {
        Observable.timer(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (requireApplication().authRepository.autoLogin) {
                    login()
                } else {
                    main()
                }
            }
            .addTo(compositeDisposable)
    }

    private fun main() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }

    private fun login() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }
}