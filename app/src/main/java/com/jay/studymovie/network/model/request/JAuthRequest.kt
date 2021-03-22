package com.jay.studymovie.network.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JAuthRequest(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("password")
    @Expose
    val password: String,
) : JRequest