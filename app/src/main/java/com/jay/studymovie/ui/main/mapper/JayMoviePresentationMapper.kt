package com.jay.studymovie.ui.main.mapper

import com.jay.studymovie.domain.model.JayMovieModel
import com.jay.studymovie.ui.main.model.JayMoviePresentation
import com.jay.studymovie.ui.mapper.JayPresentationMapper
import com.jay.studymovie.utils.ext.formatWith
import com.jay.studymovie.utils.ext.toDateWith
import java.util.*

object JayMoviePresentationMapper : JayPresentationMapper<JayMovieModel, JayMoviePresentation> {
    private const val DATE_FORMAT_YEAR = "yyyy"

    override fun mapToPresentation(from: JayMovieModel): JayMoviePresentation {
        return JayMoviePresentation(
            title = from.title,
            link = from.link,
            image = from.image,
            subtitle = from.subtitle,
            pubDate = from.pubDate.formatWith(DATE_FORMAT_YEAR),
            director = from.director,
            actor = from.actor,
            userRating = from.userRating
        )
    }

    override fun mapToModel(from: JayMoviePresentation): JayMovieModel {
        return JayMovieModel(
            title = from.title,
            link = from.link,
            image = from.image,
            subtitle = from.subtitle,
            pubDate = from.pubDate.toDateWith(DATE_FORMAT_YEAR) ?: Date(0),
            director = from.director,
            actor = from.actor,
            userRating = from.userRating
        )
    }
}