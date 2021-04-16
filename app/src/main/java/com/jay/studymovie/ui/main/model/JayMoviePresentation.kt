package com.jay.studymovie.ui.main.model

import com.jay.studymovie.ui.base.Identifiable
import com.jay.studymovie.ui.model.JayPresentation

data class JayMoviePresentation(
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Float
) : JayPresentation, Identifiable {
    override val identifier: Any
        get() = subtitle
}
