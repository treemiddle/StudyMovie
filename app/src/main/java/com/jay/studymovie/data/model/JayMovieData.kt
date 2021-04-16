package com.jay.studymovie.data.model

import java.util.*

data class JayMovieData(
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: Date,
    val director: String,
    val actor: String,
    val userRating: Float
) : JayData
