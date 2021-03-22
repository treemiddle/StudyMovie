package com.jay.studymovie.ui.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jay.studymovie.ui.application.JApplication

abstract class BaseActivity : AppCompatActivity() {

    protected open val progressView: View? = null

    protected val application: JApplication?
        get() {
            return (applicationContext as? JApplication)
        }

    fun showLoading() {
        progressView?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressView?.visibility = View.INVISIBLE
    }

    fun requireApplication(): JApplication {
        if (applicationContext is JApplication) {
            return application!!
        } else {
            throw IllegalStateException("applicationContext is not instance of JApplication")
        }
    }

}