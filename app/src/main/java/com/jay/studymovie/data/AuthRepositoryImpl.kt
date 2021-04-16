package com.jay.studymovie.data

import com.jay.studymovie.data.local.source.JayAuthLocalDataSource
import com.jay.studymovie.domain.repository.JayAuthRepository

class AuthRepositoryImpl(
    private val authLocalDataSource: JayAuthLocalDataSource
) : JayAuthRepository {
    override val autoLogin: Boolean
        get() = authLocalDataSource.autoLogin
}