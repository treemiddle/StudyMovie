package com.jay.studymovie.ui.base

interface ViewHolderLifecycle<in Model> {
    fun bind(item: Model)
    fun recycle()
}