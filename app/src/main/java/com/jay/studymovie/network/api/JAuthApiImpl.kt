package com.jay.studymovie.network.api

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.jay.studymovie.network.model.request.JAuthRequest
import io.reactivex.Completable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response

class JAuthApiImpl : JAuthApi {
    override fun postLogin(requestBody: JAuthRequest): Completable {
        return Completable.defer {
            when {
                requestBody.id != TEST_ID -> {
                    Completable.error(HttpException(provideErrorResponse(404)))
                }
                requestBody.password != TEST_PW -> {
                    Completable.error(HttpException(provideErrorResponse(409)))
                }
                else -> {
                    Completable.complete()
                }
            }
        }
    }

    private fun provideErrorResponse(code: Int): Response<Unit> {
        return Response.error(code, provideErrorBody())
    }

    private fun provideErrorBody(): ResponseBody {
        return ResponseBody.create(
            MediaType.parse("application/json"),
            "{ message: 'invalid_information' }"
        )
    }

    companion object {
        private const val TEST_ID = "0000"
        private const val TEST_PW = "0000"
    }
}