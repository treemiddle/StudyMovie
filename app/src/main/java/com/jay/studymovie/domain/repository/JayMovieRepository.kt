package com.jay.studymovie.domain.repository

import com.jay.studymovie.domain.model.JayMovieModel
import io.reactivex.Flowable

interface JayMovieRepository : JayRepository {
    val latestMovieQuery: String

    fun getMovies(query: String): Flowable<List<JayMovieModel>>
}