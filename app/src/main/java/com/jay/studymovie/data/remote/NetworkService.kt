package com.jay.studymovie.data.remote

import com.jay.studymovie.data.remote.api.NaverApi

interface NetworkService {
    val movieApi: NaverApi
}