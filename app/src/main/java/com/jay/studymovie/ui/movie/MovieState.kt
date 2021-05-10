package com.jay.studymovie.ui.movie

import com.jay.studymovie.ui.base.JayState

sealed class MovieState : JayState {
    data class ShowMovieLink(val movieLink: String) : MovieState()
}