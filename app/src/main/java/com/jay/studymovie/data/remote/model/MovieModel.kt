package com.jay.studymovie.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.studymovie.ui.base.Identifiable

data class MovieModel(
    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("link")
    @Expose
    val link: String,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("subtitle")
    @Expose
    val subtitle: String,

    @SerializedName("pubDate")
    @Expose
    val pubDate: String,

    @SerializedName("director")
    @Expose
    val director: String,

    @SerializedName("actor")
    @Expose
    val actor: String,

    @SerializedName("userRating")
    @Expose
    val userRating: String
) : Model