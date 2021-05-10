package com.jay.studymovie.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jay.studymovie.R
import com.jay.studymovie.databinding.ActivityLoginBinding
import com.jay.studymovie.ui.base.BaseActivity
import com.jay.studymovie.ui.movie.MovieActivity
import com.jay.studymovie.utils.ext.toast

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel, LoginState>(
    R.layout.activity_login
) {

    override val viewModel: LoginViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(
                    resourceProvider = requireApplication().resourceProvider
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel) {
            emptyMessage.observe(this@LoginActivity, {
                toast(it)
            })
        }
    }

    override fun onState(newState: LoginState) = when (newState) {
        is LoginState.ShowMain -> {
            goMovieSearch()
        }
    }

    private fun goMovieSearch() {
        startActivity(Intent(application, MovieActivity::class.java))
        finish()
    }

    companion object {
        const val TEST_ID = "0000"
        const val TEST_PASSWORD = "0000"
    }
}