package com.jay.studymovie.utils.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(
    value = [
        "imgSrc",
        "imgError",
        "imgPlaceholder"
    ],
    requireAll = false
)
fun ImageView.bindPoster(image: String?, error: Drawable?, placeholder: Drawable?) {
    if (image.isNullOrEmpty()) return

    Glide.with(this)
        .load(image)
        .error(error)
        .placeholder(placeholder)
        .into(this)

}