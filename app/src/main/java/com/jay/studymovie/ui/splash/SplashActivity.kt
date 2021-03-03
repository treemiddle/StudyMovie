package com.jay.studymovie.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.login.LoginActivity
import com.jay.studymovie.ui.main.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        status()
    }

    private fun status() {
        Observable.timer(2, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (requireApplication().preferencesHelper.authLogin) {
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