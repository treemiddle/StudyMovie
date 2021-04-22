package com.jay.studymovie.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jay.studymovie.databinding.ItemSearchResultBinding
import com.jay.studymovie.ui.base.BaseRecyclerViewAdapter
import com.jay.studymovie.ui.base.BaseViewHolder
import com.jay.studymovie.ui.movie.model.JayMoviePresentation

typealias OnRecyclerViewMovieItemClick = (JayMoviePresentation) -> Unit

class MovieAdapter(
    private val itemClick: OnRecyclerViewMovieItemClick? = null
) : BaseRecyclerViewAdapter<JayMoviePresentation>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<JayMoviePresentation> {
        return MovieItemHolder.create(parent).also {
            if (itemClick == null) {
                return@also
            } else {
                it.itemView.setOnClickListener { _ ->
                    val currentItem = currentList.getOrNull(it.adapterPosition) ?: return@setOnClickListener
                    itemClick.invoke(currentItem)
                }
            }
        }
    }

    class MovieItemHolder(
        private val binding: ItemSearchResultBinding
    ) : BaseViewHolder<JayMoviePresentation>(binding) {

        override fun bind(item: JayMoviePresentation) {
            super.bind(item)
        }

        companion object Factory {
            fun create(parent: ViewGroup): MovieItemHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = ItemSearchResultBinding.inflate(layoutInflater, parent, false)

                return MovieItemHolder(view)
            }
        }
    }
}