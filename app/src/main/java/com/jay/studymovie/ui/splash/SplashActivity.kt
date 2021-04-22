package com.jay.studymovie.ui.splash

import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.login.LoginActivity
import com.jay.studymovie.ui.movie.MovieActivity

class SplashActivity : BaseActivity<ViewDataBinding, SplashPresenter>(), SplashContract.View {
    override val presenter: SplashPresenter by lazy {
        SplashPresenter(
            authRepository = requireApplication().authRepository,
            view = this
        )
    }

    override fun showLogin() {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
    }

    override fun showMain() {
        startActivity(Intent(applicationContext, MovieActivity::class.java))
    }

}