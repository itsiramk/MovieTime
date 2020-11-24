package com.iram.movietime.model.movieList

import com.google.gson.annotations.SerializedName
import com.iram.movietime.db.entity.Movie

class MovieListResults(

    val results : List<Movie>,
    val page: Int,
    private val total_results: Int,
    private val dates: ReleaseDate,
    private val total_pages: Int
)