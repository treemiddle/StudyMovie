package com.jay.studymovie.data.remote.interceptor

import com.jay.studymovie.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NaverAuthInterceptor : Interceptor, JInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val requestUrl = chain.request().url().toString()

        if (requestUrl.startsWith(BuildConfig.NAVER_BASE_URL)) {
            request.addHeader(NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_ID)
            request.addHeader(NAVER_CLIENT_SECRET, BuildConfig.NAVER_CLIENT_SECRET)
        }

        return chain.proceed(request.build())
    }

    companion object {
        private const val NAVER_CLIENT_ID = "X-Naver-Client-Id"
        private const val NAVER_CLIENT_SECRET = "X-Naver-Client-Secret"
    }
}