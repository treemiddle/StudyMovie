package com.jay.studymovie.data.mapper

import com.jay.studymovie.data.model.JayMovieData
import com.jay.studymovie.domain.model.JayMovieModel

object JayMovieDataMapper : JayDataMapper<JayMovieData, JayMovieModel> {
    override fun mapToModel(from: JayMovieData): JayMovieModel {
        return JayMovieModel(
            title = from.title,
            link = from.link,
            image = from.image,
            subtitle = from.subtitle,
            pubDate = from.pubDate,
            director = from.director,
            actor = from.actor,
            userRating = from.userRating
        )
    }

    override fun mapToData(from: JayMovieModel): JayMovieData {
        return JayMovieData(
            title = from.title,
            link = from.link,
            image = from.image,
            subtitle = from.subtitle,
            pubDate = from.pubDate,
            director = from.director,
            actor = from.actor,
            userRating = from.userRating
        )
    }
}