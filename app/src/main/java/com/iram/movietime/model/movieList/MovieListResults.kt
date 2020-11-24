package com.iram.movietime.model.movieList

import com.google.gson.annotations.SerializedName
import com.iram.movietime.db.entity.Movie

class MovieListResults(
    val results : List<Movie>,
)