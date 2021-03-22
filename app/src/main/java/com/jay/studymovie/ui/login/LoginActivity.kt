package com.jay.studymovie.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.jay.studymovie.R
import com.jay.studymovie.network.model.request.JAuthRequest
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.main.MainActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {

    private lateinit var idInputEditText: TextInputEditText
    private lateinit var passwordInputEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var loadingProgressBar: ProgressBar

    private val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)

    override val progressView: View?
        get() = loadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        binxRx()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun initView() {
        idInputEditText = findViewById(R.id.iet_id)
        passwordInputEditText = findViewById(R.id.iet_password)
        loginButton = findViewById(R.id.btn_login)
        loadingProgressBar = findViewById(R.id.pgb_loading)
    }

    private fun binxRx() {
        val idChanges = idInputEditText.textChanges()
            .map(CharSequence::toString)
        val pwChanges = passwordInputEditText.textChanges()
            .map(CharSequence::toString)
        val request = Observable.combineLatest(
            idChanges,
            pwChanges,
            { id, password ->
                JAuthRequest(id, password)
            }
        )

        loginButton.clicks()
            .throttleFirst(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .doOnNext { clearError() }
            .flatMap { request }
            .doOnNext { showLoading() }
            .switchMap {
                requireApplication().networkService.authApi
                    .postLogin(it)
                    .toSingleDefault(Unit)
                    .toObservable()
            }
            .doOnNext { hideLoading() }
            .doOnError { hideLoading() }
            .doOnError(this::showError)
            .retry { error -> (error is HttpException) }
            .subscribe { routeMovieSearch() }
            .addTo(compositeDisposable)
    }

    private fun clearError() {
        idInputEditText.error = null
        passwordInputEditText.error = null
    }

    private fun showError(throwable: Throwable) {
        when (throwable) {
            is HttpException -> when (throwable.code()) {
                404 -> {
                    idInputEditText.error = "존재히자 않는 사용자입니다"
                }
                409 -> {
                    passwordInputEditText.error = "패스워드가 틀렸습니다"
                }
                else -> {
                    clearError()
                }
            }
            else -> {
                clearError()
            }
        }
    }

    private fun routeMovieSearch() {
        requireApplication().preferencesHelper.authLogin = false

        startActivity(Intent(application, MainActivity::class.java))
        finish()
    }

}