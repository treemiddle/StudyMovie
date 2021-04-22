package com.jay.studymovie.ui.base

import com.jay.studymovie.ui.model.JayPresentation

interface ViewHolderLifecycle<T : JayPresentation> {
    fun bind(item: T)
    fun recycle()
}