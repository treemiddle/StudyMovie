package com.jay.studymovie.ui.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jay.studymovie.network.model.Model

typealias OnRecyclerViewItemClick<E> = ((E) -> Unit)

abstract class BaseRecyclerViewAdapter<E, VH> : ListAdapter<E, VH>(object : DiffUtil.ItemCallback<E>() {
    override fun areItemsTheSame(oldItem: E, newItem: E): Boolean {
        return oldItem.identifier == newItem.identifier
    }

    override fun areContentsTheSame(oldItem: E, newItem: E): Boolean {
        return oldItem == newItem
    }
}) where E : Identifiable, E : Model, VH : BaseViewHolder {

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: VH, position: Int) {
        val currentItem = currentList.getOrNull(position) ?: return
        (holder as? ViewHolderLifecycle<E>)?.bind(currentItem)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        (holder as? ViewHolderLifecycle<E>)?.recycle()
    }

}