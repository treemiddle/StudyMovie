package com.jay.studymovie.utils.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jay.studymovie.ui.base.BaseRecyclerViewAdapter
import com.jay.studymovie.ui.base.Identifiable

@BindingAdapter("movieList")
fun <T : Identifiable> RecyclerView.bindMovieList(movies: List<T>?) {
    if (this.adapter is BaseRecyclerViewAdapter<*>) {
        val adapter = this.adapter as BaseRecyclerViewAdapter<T>
        adapter.submitList(movies)
    }
}