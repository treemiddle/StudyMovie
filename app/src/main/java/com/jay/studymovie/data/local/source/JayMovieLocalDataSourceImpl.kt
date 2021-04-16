package com.jay.studymovie.data.local.source

import com.jay.studymovie.data.local.dao.JayMovieDao
import com.jay.studymovie.data.local.mapper.JayMovieLocalMapper
import com.jay.studymovie.data.local.prefs.PreferencesHelper
import com.jay.studymovie.data.model.JayMovieData
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class JayMovieLocalDataSourceImpl(
    private val prefs: PreferencesHelper,
    private val movieDao: JayMovieDao
) : JayMovieLocalDataSource {
    override var latestMovieQuery: String
        get() = prefs.latestMovieQuery
        set(value) {
            prefs.latestMovieQuery = value
        }

    override fun getMovies(): Single<List<JayMovieData>> {
        return movieDao.getMovies()
            .map { it.map(JayMovieLocalMapper::mapToData) }
            .subscribeOn(Schedulers.io())
    }

    override fun saveMovies(movies: List<JayMovieData>): Completable {
        return movieDao.deleteAll()
            .andThen(Single.just(movies))
            .map { it.map(JayMovieLocalMapper::mapToLocal) }
            .flatMapCompletable(movieDao::insertMovies)
            .subscribeOn(Schedulers.io())
    }
}