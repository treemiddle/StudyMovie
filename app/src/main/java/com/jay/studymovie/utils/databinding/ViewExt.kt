package com.jay.studymovie.utils.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import io.reactivex.subjects.Subject

@BindingAdapter("onClick")
fun View.bindClick(listener: View.OnClickListener) {
    setOnClickListener(listener)
}

@BindingAdapter("visibleOrInvisible")
fun View.bindIsLoading(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}