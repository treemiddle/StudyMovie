package com.jay.studymovie.ui.splash

import com.jay.studymovie.domain.repository.JayAuthRepository

class SplashPresenter(
    private val authRepository: JayAuthRepository,
    private val view: SplashContract.View
) : SplashContract.Presenter {

    override fun create() {
        val autoLogin = authRepository.autoLogin

        if (autoLogin) {
            view.showMain()
        } else {
            view.showLogin()
        }
    }
}