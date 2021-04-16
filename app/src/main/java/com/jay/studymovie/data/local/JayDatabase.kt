package com.jay.studymovie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jay.studymovie.data.local.converter.JayDateTypeConverter
import com.jay.studymovie.data.local.dao.JayMovieDao
import com.jay.studymovie.data.local.model.JayMovieEntity
import com.jay.studymovie.data.model.JayData

@Database(
    entities = [JayMovieEntity::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(value = [JayDateTypeConverter::class])
abstract class JayDatabase : RoomDatabase() {
    abstract val movieDao: JayMovieDao

    object Factory {
        private const val DATABASE_NAME = "io.github.jay.local"

        fun create(applicationContext: Context): JayDatabase {
            return Room.databaseBuilder(
                applicationContext.applicationContext,
                JayDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}