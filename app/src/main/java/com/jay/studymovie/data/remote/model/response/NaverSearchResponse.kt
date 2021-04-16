package com.jay.studymovie.data.remote.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jay.studymovie.data.remote.model.Model
import java.util.*

data class NaverSearchResponse<T : Model>(
    @SerializedName("total")
    @Expose
    val total: Int,

    @SerializedName("start")
    @Expose
    val start: Int,

    @SerializedName("display")
    @Expose
    val display: Int,

    @SerializedName("lastBuildDate")
    @Expose
    val lastBuildDate: Date,

    @SerializedName("items")
    @Expose
    val items: List<T>
) : Response