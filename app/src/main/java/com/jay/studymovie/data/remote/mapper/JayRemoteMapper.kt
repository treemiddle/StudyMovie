package com.jay.studymovie.data.remote.mapper

import com.jay.studymovie.data.model.JayData
import com.jay.studymovie.data.remote.model.Model

interface JayRemoteMapper<R : Model, D : JayData> {
    fun mapToRemote(from: D): R

    fun mapToData(from: R): D
}