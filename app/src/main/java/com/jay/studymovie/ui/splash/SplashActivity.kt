package com.jay.studymovie.ui.splash

import android.content.Intent
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.login.LoginActivity
import com.jay.studymovie.ui.movie.MovieActivity

class SplashActivity : BaseActivity<ViewDataBinding, SplashViewModel, SplashState>() {

    override val viewModel: SplashViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SplashViewModel(
                    authRepository = requireApplication().authRepository
                ) as T
            }
        }
    }

    override fun onState(newState: SplashState) {
        when (newState) {
            is SplashState.ShowLogin -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            is SplashState.ShowMain -> {
                startActivity(Intent(this, MovieActivity::class.java))
                finish()
            }
        }
    }

}