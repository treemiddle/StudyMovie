package com.jay.studymovie.domain.model

import java.util.*

data class JayMovieModel(
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: Date,
    val director: String,
    val actor: String,
    val userRating: Float
) : JayModel
