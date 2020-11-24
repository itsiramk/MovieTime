package com.iram.movietime.model.movieList

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReleaseDate(
    private var maximum: String,
    private val minimum: String
)