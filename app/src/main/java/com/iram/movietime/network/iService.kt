package com.iram.movietime.network

import com.iram.movietime.db.entity.Movie
import com.iram.movietime.model.MovieListResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface iService
{

    @GET("now_playing")
    suspend fun getMoviesList(@Query("api_key")apiKey:String): Response<MovieListResults>

}