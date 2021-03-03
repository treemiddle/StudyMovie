package com.jay.studymovie.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.jay.studymovie.R
import com.jay.studymovie.network.model.MovieModel
import com.jay.studymovie.ui.base.BaseRecyclerViewAdapter
import com.jay.studymovie.ui.base.BaseViewHolder
import com.jay.studymovie.ui.base.OnRecyclerViewItemClick
import com.jay.studymovie.ui.base.ViewHolderLifecycle

class MainAdapter(
    private val onItemClick: OnRecyclerViewItemClick<MovieModel>? = null
) : BaseRecyclerViewAdapter<MovieModel, MainAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent).also {
            if (onItemClick == null) {
                return@also
            } else {
                it.itemView.setOnClickListener { _ ->
                    val currentItem = currentList[it.adapterPosition] ?: return@setOnClickListener
                    onItemClick.invoke(currentItem)
                }
            }
        }
    }

    class ItemViewHolder(itemView: View) : BaseViewHolder(itemView), ViewHolderLifecycle<MovieModel> {
        private val ivPoster: ImageView = itemView.findViewById(R.id.iv_poster)
        private val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private val rbStar: RatingBar = itemView.findViewById(R.id.rb_star)
        private val tvPublishDate: TextView = itemView.findViewById(R.id.tv_publish_date)
        private val tvDirector: TextView = itemView.findViewById(R.id.tv_director)
        private val tvActor: TextView = itemView.findViewById(R.id.tv_actor)

        override fun bind(item: MovieModel) {
            Glide.with(ivPoster)
                .load(item.image)
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_load)
                .into(ivPoster)

            tvTitle.text = HtmlCompat.fromHtml(item.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
            rbStar.rating = (item.userRating.toFloatOrNull() ?: 0f) / 2
            tvPublishDate.text = item.pubDate
            tvDirector.text = item.director
            tvActor.text = item.actor
        }

        override fun recycle() {
            Glide.with(ivPoster).clear(ivPoster)
        }

        companion object Factory {
            fun from(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_search_result, parent, false)

                return ItemViewHolder(view)
            }
        }
    }
}