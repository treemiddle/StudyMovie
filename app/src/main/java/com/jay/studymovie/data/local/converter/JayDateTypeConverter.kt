package com.jay.studymovie.data.local.converter

import androidx.room.TypeConverter
import java.util.*

object JayDateTypeConverter {

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    @JvmStatic
    fun toDate(time: Long): Date {
        return Date(time)
    }
}