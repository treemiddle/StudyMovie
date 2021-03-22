package com.jay.studymovie.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jay.studymovie.BuildConfig
import com.jay.studymovie.network.api.JAuthApi
import com.jay.studymovie.network.api.JAuthApiImpl
import com.jay.studymovie.network.api.NaverApi
import com.jay.studymovie.network.interceptor.NaverAuthInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkServiceImpl : NetworkService {

    override val movieApi: NaverApi by lazy {
        retrofit.create(NaverApi::class.java)
    }

    override val authApi: JAuthApi by lazy {
        JAuthApiImpl()
    }

    private val authInterceptor: NaverAuthInterceptor by lazy {
        NaverAuthInterceptor()
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    private val gson: Gson by lazy {
        GsonBuilder()
            .setDateFormat(GSON_DATE_FORMAT)
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        private const val GSON_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss Z"
    }
}