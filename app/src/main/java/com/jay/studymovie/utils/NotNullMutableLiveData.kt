package com.jay.studymovie.utils

import androidx.lifecycle.MutableLiveData

class NotNullMutableLiveData<E : Any>(
    defaultValue: E
) : MutableLiveData<E>(defaultValue) {
    override fun getValue(): E {
        return super.getValue()!!
    }
}