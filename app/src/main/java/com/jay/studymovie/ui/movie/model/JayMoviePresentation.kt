package com.jay.studymovie.ui.movie.model

import com.jay.studymovie.ui.base.Identifiable
import com.jay.studymovie.ui.base.JayClickable
import com.jay.studymovie.ui.model.JayPresentation
import io.reactivex.subjects.Subject

data class JayMoviePresentation(
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Float
) : JayPresentation, Identifiable, JayClickable<JayMoviePresentation> {
    override val identifier: Any
        get() = subtitle

    override lateinit var onClick: Subject<JayMoviePresentation>
}
