package com.jay.studymovie.network

import com.jay.studymovie.network.api.JAuthApi
import com.jay.studymovie.network.api.NaverApi

interface NetworkService {
    val movieApi: NaverApi
    val authApi: JAuthApi
}