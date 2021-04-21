package com.jay.studymovie.ui.login

import com.jay.studymovie.ui.base.JayBasePresenter
import com.jay.studymovie.ui.base.JayBaseView

interface LoginContract {

    interface View : JayBaseView {
        fun clearError()
        fun showIdError()
        fun showPasswordError()
        fun loginSuccess()
        fun emptyError()
        fun goMovieSearch()
        fun onNextId(id: String)
        fun onNextPassword(password: String)
        fun onLoginClick()
    }

    interface Presenter : JayBasePresenter {
        fun login(id: String, password: String)
    }
}