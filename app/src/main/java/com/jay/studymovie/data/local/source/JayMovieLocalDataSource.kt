package com.jay.studymovie.data.local.source

import com.jay.studymovie.data.model.JayMovieData
import io.reactivex.Completable
import io.reactivex.Single

interface JayMovieLocalDataSource : JayLocalDataSource {
    var latestMovieQuery: String

    fun getMovies(): Single<List<JayMovieData>>

    fun saveMovies(movies: List<JayMovieData>): Completable
}