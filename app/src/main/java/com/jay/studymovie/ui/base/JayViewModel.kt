package com.jay.studymovie.ui.base

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jay.studymovie.utils.SingleEvent
import com.jay.studymovie.utils.ext.liveOf
import io.reactivex.disposables.CompositeDisposable

open class JayViewModel<S: JayState> : ViewModel(), JayViewModelLifecycle {
    protected val compositeDisposable: CompositeDisposable by lazy(::CompositeDisposable)

    private val _state: MutableLiveData<SingleEvent<S>> by liveOf()
    val state: LiveData<SingleEvent<S>>
        get() = _state
    
    @MainThread
    protected fun runState(state: S) {
        _state.value = SingleEvent(state)
    }

    protected fun postState(state: S) {
        _state.postValue(SingleEvent(state))
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}