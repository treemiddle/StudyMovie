package com.jay.studymovie.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.jay.studymovie.R
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.main.MainActivity
import com.jay.studymovie.utils.ext.toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity() {

    private lateinit var idInputEditText: TextInputEditText
    private lateinit var passwordInputEditText: TextInputEditText
    private lateinit var loginButton: Button
    private lateinit var loadingProgressBar: ProgressBar

    private val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)
    private val _idSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val _passwordSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val _loginSubject: Subject<Unit> = PublishSubject.create()

    override val progressView: View?
        get() = loadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
        initListener()
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

    private fun initListener() {
        idInputEditText.addTextChangedListener { idOnNext(it.toString()) }
        passwordInputEditText.addTextChangedListener { passwordOnNext(it.toString()) }
        loginButton.setOnClickListener { onLoginClick() }
    }

    private fun binxRx() {
        _loginSubject.throttleFirst(2, TimeUnit.SECONDS)
            .map { _idSubject.value to _passwordSubject.value }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { (id, password) ->
                when {
                    id.isNullOrEmpty() || password.isNullOrEmpty() -> {
                        showError(500)
                    }
                    id != TEST_ID -> {
                        showError(404)
                    }
                    password != TEST_PASSWORD -> {
                        showError(409)
                    }
                    else -> {
                        showError(200)
                    }
                }
            }
            .addTo(compositeDisposable)
    }

    private fun idOnNext(id: String) = _idSubject.onNext(id)

    private fun passwordOnNext(password: String) = _passwordSubject.onNext(password)

    private fun onLoginClick() = _loginSubject.onNext(Unit)

    private fun clearError() {
        idInputEditText.error = null
        passwordInputEditText.error = null
    }

    private fun showError(code: Int) {
        when (code) {
            200 -> {
                clearError()
                routeMovieSearch()
            }
            404 -> {
                idInputEditText.error = getString(R.string.login_fail_id)
            }
            409 -> {
                passwordInputEditText.error = getString(R.string.login_fail_password)
            }
            else -> {
                this.toast(getString(R.string.login_empty))
            }
        }
    }

    private fun routeMovieSearch() {
        requireApplication().preferencesHelper.authLogin = false

        startActivity(Intent(application, MainActivity::class.java))
        finish()
    }

    companion object {
        private const val TEST_ID = "0000"
        private const val TEST_PASSWORD = "0000"
    }
}