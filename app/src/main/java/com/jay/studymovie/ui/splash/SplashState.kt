package com.jay.studymovie.ui.splash

import com.jay.studymovie.ui.base.JayState

sealed class SplashState : JayState {
    object ShowMain : SplashState()

    object ShowLogin : SplashState()
}