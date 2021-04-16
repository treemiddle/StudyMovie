package com.jay.studymovie.data.local.mapper

import com.jay.studymovie.data.local.model.JayEntity as LocalModel
import com.jay.studymovie.data.model.JayData

interface JayLocalMapper<L : LocalModel, D : JayData> {
    fun mapToLocal(from: D): L

    fun mapToData(from: L): D
}