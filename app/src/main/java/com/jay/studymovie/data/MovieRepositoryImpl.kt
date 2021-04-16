package com.jay.studymovie.data

import com.jay.studymovie.data.local.source.JayMovieLocalDataSource
import com.jay.studymovie.data.mapper.JayMovieDataMapper
import com.jay.studymovie.data.model.JayMovieData
import com.jay.studymovie.data.remote.source.JayMovieRemoteDataSource
import com.jay.studymovie.domain.model.JayMovieModel
import com.jay.studymovie.domain.repository.JayMovieRepository
import io.reactivex.Flowable
import io.reactivex.Single

class MovieRepositoryImpl(
    private val movieLocalDataSource: JayMovieLocalDataSource,
    private val movieRemoteDataSource: JayMovieRemoteDataSource
) : JayMovieRepository {
    override val latestMovieQuery: String
        get() = movieLocalDataSource.latestMovieQuery

    override fun getMovies(query: String): Flowable<List<JayMovieModel>> {
        movieLocalDataSource.latestMovieQuery = query
        return movieLocalDataSource.getMovies()
            .onErrorReturn { listOf() }
            .flatMapPublisher { cachedMovies ->
                if (cachedMovies.isEmpty()) {
                    getRemoteMovies(query)
                        .map { it.map(JayMovieDataMapper::mapToModel) }
                        .toFlowable()
                        .onErrorReturn { listOf() }
                } else {
                    val local = Single.just(cachedMovies)
                        .map { it.map(JayMovieDataMapper::mapToModel) }
                    val remote = getRemoteMovies(query)
                        .map { it.map(JayMovieDataMapper::mapToModel) }
                        .onErrorResumeNext { local }
                    Single.concat(local, remote)
                }
            }
    }

    private fun getRemoteMovies(query: String): Single<List<JayMovieData>> {
        return movieRemoteDataSource.getMovies(query)
            .flatMap { remoteMovies ->
                movieLocalDataSource.saveMovies(remoteMovies)
                    .andThen(Single.just(remoteMovies))
            }
    }
}