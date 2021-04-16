package com.jay.studymovie.data.remote.api

import com.jay.studymovie.data.remote.model.MovieModel
import com.jay.studymovie.data.remote.model.response.NaverSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {
    @GET("v1/search/movie.json")
    fun getSearchMovie(
        @Query("query") query: String)
    : Single<NaverSearchResponse<MovieModel>>
}