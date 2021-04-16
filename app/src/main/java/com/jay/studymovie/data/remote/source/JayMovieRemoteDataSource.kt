package com.jay.studymovie.data.remote.source

import com.jay.studymovie.data.model.JayMovieData
import io.reactivex.Single

interface JayMovieRemoteDataSource : JayRemoteDataSource {
    fun getMovies(query: String): Single<List<JayMovieData>>
}