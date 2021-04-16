package com.jay.studymovie.domain.repository

import io.reactivex.Flowable

interface JayAuthRepository : JayRepository {
    val autoLogin: Boolean
}