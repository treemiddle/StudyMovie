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
import com.jay.studymovie.BR
import com.jay.studymovie.ui.application.JApplication

abstract class BaseActivity<VDB : ViewDataBinding, VM : JayViewModel<*>, S : JayState>(
    @LayoutRes
    val layoutResdId: Int = 0
) : AppCompatActivity() {
    protected lateinit var binding: VDB
    protected abstract val viewModel: VM
    protected val application: JApplication?
        get() = (applicationContext as? JApplication)

    init {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> {
                        viewModel.create()
                    }
                    Lifecycle.Event.ON_START -> {
                        viewModel.start()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        viewModel.resume()
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        viewModel.pause()
                    }
                    Lifecycle.Event.ON_STOP -> {
                        viewModel.stop()
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        lifecycle.removeObserver(this)
                    }
                    else -> {
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
                setVariable(BR.viewModel, viewModel)
            }
        }

        viewModel.state.observe(this, {
            if (it != null && it.isConsumed.compareAndSet(false, true)) {
                @Suppress("UNCHECKED_CAST")
                onState(it.event as S)
            }
        })
    }

    protected open fun onState(newState: S) {

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