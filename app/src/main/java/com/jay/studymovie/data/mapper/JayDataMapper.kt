package com.jay.studymovie.data.mapper

import com.jay.studymovie.data.model.JayData
import com.jay.studymovie.domain.model.JayModel

interface JayDataMapper<D : JayData, M : JayModel> {
    fun mapToModel(from: D): M

    fun mapToData(from: M): D
}