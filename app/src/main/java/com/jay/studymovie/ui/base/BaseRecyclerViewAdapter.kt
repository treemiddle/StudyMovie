package com.jay.studymovie.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseRecyclerViewAdapter<T : Identifiable> : ListAdapter<T, BaseViewHolder<T>>(object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}) {

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewRecycled(holder: BaseViewHolder<T>) {
        holder.recycle()
    }
}