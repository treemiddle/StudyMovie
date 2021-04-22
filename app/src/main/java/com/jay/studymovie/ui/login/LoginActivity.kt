package com.jay.studymovie.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.jay.studymovie.R
import com.jay.studymovie.databinding.ActivityLoginBinding
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.movie.MovieActivity
import com.jay.studymovie.utils.ext.toast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginPresenter>(R.layout.activity_login), LoginContract.View {

    override val presenter: LoginPresenter by lazy {
        LoginPresenter(
            view = this
        )
    }
    private val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)
    private val _idSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val _passwordSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val _loginSubject: Subject<Unit> = PublishSubject.create()

    override val commonProgressView: View?
        get() = binding.pgbLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListener()
    }

    override fun clearError() {
        binding.ietId.error = null
        binding.ietPassword.error = null
    }

    override fun showIdError() {
        binding.ietId.error = getString(R.string.login_fail_id)
    }

    override fun showPasswordError() {
        binding.ietPassword.error = getString(R.string.login_fail_password)
    }

    override fun loginSuccess() {
        goMovieSearch()
    }

    override fun emptyError() {
        this.toast(getString(R.string.login_empty))
    }

    override fun goMovieSearch() {
        startActivity(Intent(application, MovieActivity::class.java))
        finish()
    }

    override fun onNextId(id: String) = _idSubject.onNext(id)

    override fun onNextPassword(password: String) = _passwordSubject.onNext(password)

    override fun onLoginClick() = _loginSubject.onNext(Unit)

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun initListener() {
        with(binding) {
            ietId.addTextChangedListener { onNextId(it.toString()) }
            ietPassword.addTextChangedListener { onNextPassword(it.toString()) }
            btnLogin.setOnClickListener {
                presenter.login(
                    id = _idSubject.value!!,
                    password = _passwordSubject.value!!
                )
            }
        }
    }

    companion object {
        const val TEST_ID = "0000"
        const val TEST_PASSWORD = "0000"
    }
}