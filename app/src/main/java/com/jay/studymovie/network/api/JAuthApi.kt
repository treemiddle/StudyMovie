package com.jay.studymovie.network.api

import com.jay.studymovie.network.model.request.JAuthRequest
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface JAuthApi {
    @POST("/auth")
    fun postLogin(@Body requestBody: JAuthRequest): Completable
}