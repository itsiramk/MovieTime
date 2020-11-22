package com.iram.movietime.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.iram.movietime.db.entity.Movie

public class MovieListResults(

    val results : List<Movie>,
    val page: Int,

    @SerializedName("total_results")
    private val totalResults: Int,

    private val dates: ReleaseDate,

    @SerializedName("total_pages")
    private val totalPages: Int
)