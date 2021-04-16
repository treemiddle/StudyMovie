package com.jay.studymovie.data.remote.source

import com.jay.studymovie.data.model.JayMovieData
import com.jay.studymovie.data.remote.api.NaverApi
import com.jay.studymovie.data.remote.mapper.JayMovieRemoteMapper
import io.reactivex.Single

class JayMovieRemoteDataSourceImpl(
    private val naverApi: NaverApi
) : JayMovieRemoteDataSource {
    override fun getMovies(query: String): Single<List<JayMovieData>> {
        return naverApi.getSearchMovie(query)
            .map { it.items.map(JayMovieRemoteMapper::mapToData) }
    }
}