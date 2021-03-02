package com.jay.studymovie.network.api

import com.jay.studymovie.network.model.Model
import com.jay.studymovie.network.model.response.NaverSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverApi {
    @GET("v1/search/movie.json")
    fun getSearchMovie(
        @Query("query") query: String)
    : Single<NaverSearchResponse<Model>>
}