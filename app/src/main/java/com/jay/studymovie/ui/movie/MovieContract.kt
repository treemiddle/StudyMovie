package com.jay.studymovie.ui.movie

import android.net.Uri
import com.jay.studymovie.ui.base.JayBasePresenter
import com.jay.studymovie.ui.base.JayBaseView
import com.jay.studymovie.ui.movie.model.JayMoviePresentation

interface MovieContract {

    interface View : JayBaseView {
        fun setLatestQuery(query: String)
        fun showMovieList(items: List<JayMoviePresentation>)
        fun movieItemClick(uri: Uri)
    }

    interface Presenter : JayBasePresenter {
        fun debounceQuery(query: String)
        fun buttonClickQuery()
        fun searchResultClick(item: JayMoviePresentation)
    }
}