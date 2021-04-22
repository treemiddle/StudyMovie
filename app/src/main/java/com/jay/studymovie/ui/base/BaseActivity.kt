package com.jay.studymovie.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.jay.studymovie.ui.application.JApplication

abstract class BaseActivity<VDB : ViewDataBinding, Presenter : JayBasePresenter>(
    @LayoutRes
    val layoutResdId: Int = 0
) : AppCompatActivity(), JayBaseView {
    protected lateinit var binding: VDB
    protected abstract val presenter: Presenter
    protected open val commonProgressView: View? = null
    protected val application: JApplication?
        get() = (applicationContext as? JApplication)

    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        presenter.create()
                    }
                    Lifecycle.Event.ON_START -> {
                        presenter.start()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        presenter.resume()
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        presenter.pause()
                    }
                    Lifecycle.Event.ON_STOP -> {
                        presenter.stop()
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        presenter.destroy()
                    }
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (layoutResdId != 0) {
            binding = DataBindingUtil.setContentView<VDB>(this, layoutResdId).apply {
                lifecycleOwner = this@BaseActivity
            }
        }
    }

    override fun showLoading() {
        commonProgressView?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        commonProgressView?.visibility = View.INVISIBLE
    }

    @Throws(IllegalStateException::class)
    fun requireApplication(): JApplication {
        if (applicationContext is JApplication) {
            return application!!
        } else {
            throw IllegalStateException("applicationContext is not instance of SAApplication")
        }
    }

}