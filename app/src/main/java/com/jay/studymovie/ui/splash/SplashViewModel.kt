package com.jay.studymovie.ui.splash

import com.jay.studymovie.domain.repository.JayAuthRepository
import com.jay.studymovie.ui.base.JayViewModel

class SplashViewModel(
    authRepository : JayAuthRepository
) : JayViewModel<SplashState>() {

    init {
        val autoLogin = authRepository.autoLogin

        if (autoLogin) {
            runState(SplashState.ShowMain)
        } else {
            runState(SplashState.ShowLogin)
        }
    }
}