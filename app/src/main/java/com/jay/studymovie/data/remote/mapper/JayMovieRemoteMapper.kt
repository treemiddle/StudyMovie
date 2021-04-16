package com.jay.studymovie.data.remote.mapper

import com.jay.studymovie.data.model.JayMovieData
import com.jay.studymovie.utils.ext.formatWith
import com.jay.studymovie.utils.ext.toDateWith
import com.jay.studymovie.utils.ext.toPlainFromHtml
import java.util.*
import com.jay.studymovie.data.remote.model.MovieModel as RemoteModel

object JayMovieRemoteMapper : JayRemoteMapper<RemoteModel, JayMovieData> {
    private const val DATE_FORMAT_YEAR = "yyyy"

    override fun mapToRemote(from: JayMovieData): RemoteModel {
        return RemoteModel(
            title = from.title,
            link = from.link,
            image = from.image,
            subtitle = from.subtitle,
            pubDate = from.pubDate.formatWith(DATE_FORMAT_YEAR),
            director = from.director,
            actor = from.actor,
            userRating = from.userRating.toString()
        )
    }

    override fun mapToData(from: RemoteModel): JayMovieData {
        return JayMovieData(
            title = from.title.toPlainFromHtml(),
            link = from.link,
            image = from.image,
            subtitle = from.subtitle.toPlainFromHtml(),
            pubDate = from.pubDate.toDateWith(DATE_FORMAT_YEAR) ?: Date(0),
            director = from.director.toPlainFromHtml(),
            actor = from.actor.toPlainFromHtml(),
            userRating = from.userRating.toFloatOrNull() ?: 0f
        )
    }
}