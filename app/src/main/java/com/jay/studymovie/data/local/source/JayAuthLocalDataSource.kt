package com.jay.studymovie.data.local.source

interface JayAuthLocalDataSource {
    val autoLogin: Boolean

    fun saveAuth()
}