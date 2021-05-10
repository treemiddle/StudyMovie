package com.jay.studymovie.utils.ext

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jay.studymovie.utils.NotNullMutableLiveData

fun <E : Any> ViewModel.liveOf(defaultValue: E? = null) = lazy {
    MutableLiveData<E>(defaultValue)
}

fun <E : Any> ViewModel.notNullLiveOf(defaultValue: E) = lazy {
    NotNullMutableLiveData(defaultValue)
}