package com.jay.studymovie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.jay.studymovie.data.local.model.JayMovieEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface JayMovieDao {
    @Query("SELECT * FROM movie")
    fun getMovies(): Single<List<JayMovieEntity>>

    @Insert(onConflict = REPLACE)
    fun insertMovies(movies: List<JayMovieEntity>): Completable

    @Query("DELETE from movie")
    fun deleteAll(): Completable
}