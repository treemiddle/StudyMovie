package com.jay.studymovie.ui.mapper

import com.jay.studymovie.domain.model.JayModel
import com.jay.studymovie.ui.model.JayPresentation

interface JayPresentationMapper<M : JayModel, P : JayPresentation> {
    fun mapToPresentation(from: M): P

    fun mapToModel(from: P): M
}