package com.jay.studymovie.ui.login

import com.jay.studymovie.ui.base.JayState

sealed class LoginState : JayState {
    object ShowMain : LoginState()
}