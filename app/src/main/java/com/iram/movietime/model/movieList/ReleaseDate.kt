package com.iram.movietime.model.movieList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReleaseDate(
    @SerializedName("maximum")
    @Expose
    private var maximum: String,
    @SerializedName("minimum")
    @Expose
    private val minimum: String
)