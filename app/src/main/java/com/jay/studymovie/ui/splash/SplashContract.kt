package com.jay.studymovie.ui.splash

import com.jay.studymovie.ui.base.JayBasePresenter
import com.jay.studymovie.ui.base.JayBaseView

interface SplashContract {

    interface View : JayBaseView {
        fun showLogin()
        fun showMain()
    }

    interface Presenter : JayBasePresenter {

    }
}