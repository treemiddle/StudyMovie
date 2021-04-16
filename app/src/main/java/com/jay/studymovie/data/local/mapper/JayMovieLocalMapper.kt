package com.jay.studymovie.data.local.mapper

import com.jay.studymovie.data.local.model.JayMovieEntity as JayMovieLocalModel
import com.jay.studymovie.data.model.JayMovieData

object JayMovieLocalMapper : JayLocalMapper<JayMovieLocalModel, JayMovieData> {
    override fun mapToLocal(from: JayMovieData): JayMovieLocalModel {
        return JayMovieLocalModel(
            id = 0L,
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

    override fun mapToData(from: JayMovieLocalModel): JayMovieData {
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