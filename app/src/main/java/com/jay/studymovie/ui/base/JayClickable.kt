package com.jay.studymovie.ui.base

import com.jay.studymovie.ui.model.JayPresentation
import io.reactivex.subjects.Subject

interface JayClickable<E : JayPresentation> {
    var onClick: Subject<E>
}